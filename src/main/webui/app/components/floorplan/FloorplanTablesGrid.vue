<script setup>
import { getApiFloorsFloorIdTables } from '~/services/floor-resource/floor-resource';
import { deleteApiTablesUuid, putApiTablesUuid } from '~/services/restaurant-table-resource/restaurant-table-resource';

const toast = useToast()
const currentFloor = useState('admin-current-floor')
const isDragActive = ref(false)

const { data: tables, refresh: refreshTables } = useAsyncData(`getApiFloorsFloorIdTables-${currentFloor.value.id}`,
    () => getApiFloorsFloorIdTables(currentFloor.value.id))

const dragStartHandler = (table) => (e) => {
    isDragActive.value = true
    e.dataTransfer.setData("text/plain", table.id)
}

const dragEndHandler = () =>
    isDragActive.value = false

const dropHandler = (idx) => (e) => {
    const x = idx % currentFloor.value.gridWidth
    const y = Math.floor(idx / currentFloor.value.gridWidth)
    const tableId = e.dataTransfer.getData("text/plain")
    const table = tables.value.find(table => table.id === tableId)
    putApiTablesUuid(tableId, {
        posX: x,
        posY: y,
        name: table.name,
        status: table.status,
        flooUuid: table.floor.id,
    })
        .then(_ => refreshNuxtData(`getApiFloorsFloorIdTables-${currentFloor.value.id}`))
}

const deleteTable = (tableId) => deleteApiTablesUuid(tableId)
    .then(_ => refreshNuxtData(`getApiFloorsFloorIdTables-${currentFloor.value.id}`))
    .then(_ => toast.info({ message: 'Mesa eliminada correctamente' }))
    .then(_ => document.activeElement.blur())

const arrangedTables = computed(() => arrangeTables(currentFloor.value, tables.value ?? []))

watch(currentFloor, _ => refreshTables())
</script>

<template>
    <div class="w-full grid gap-1" :style="{
        'grid-template-columns': `repeat(${currentFloor.gridWidth}, 1fr)`,
        'grid-template-rows': `repeat(${currentFloor.gridHeight}, 1fr)`,
    }">
        <div v-for="(table, idx) in arrangedTables" class="contents">
            <div v-if="!table" class="aspect-square bg-base-100 rounded-lg"
                :class="{ 'border border-gray-600 border-dashed': isDragActive }"
                @drop.prevent="e => dropHandler(idx)(e)" @dragover.prevent="() => false"></div>
            <template v-else-if="table.status == 'AVAILABLE'">
                <div :id="table.id" draggable="true" @dragstart="e => dragStartHandler(table)(e)"
                    @dragend="dragEndHandler" class="dropdown dropdown-center">
                    <div tabindex="0" role="button"
                        class="grid place-content-center aspect-square bg-base-100 rounded-lg border border-primary cursor-pointer">
                        {{ table.name }}
                    </div>
                    <ul tabindex="-1" :class="{ 'hidden': isDragActive }"
                        class="dropdown-content menu bg-base-100 rounded-box z-1 w-52 p-2 shadow-sm">
                        <li><a @click="deleteTable(table.id)">Eliminar</a></li>
                    </ul>
                </div>
            </template>
        </div>
    </div>
</template>
