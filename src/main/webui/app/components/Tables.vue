<script setup>
import { getApiFloors, getApiFloorsFloorIdTables } from '~/services/floor-resource/floor-resource';
import { putApiTablesUuidOpen } from '~/services/restaurant-table-resource/restaurant-table-resource';

const toast = useToast()
const session = useSession()
const currentFloor = useState(`${session.value.id}-current-floor`, () => null)
const isLgScreen = useMediaQuery('(min-width: 1024px)')
const size = useState(`${session.value.id}-current-floor-size`, () => ({ width: 0, height: 0 }))

const { data: floors } = useAsyncData('getApiFloors', () => getApiFloors())

const { data: tables, refresh: refreshTables } = useAsyncData(`getApiFloorsFloorIdTables-${currentFloor.value?.id}`,
    () => getApiFloorsFloorIdTables(currentFloor.value.id), { immediate: currentFloor.value })

const openTable = (table) => putApiTablesUuidOpen(table.id)
    .then(table => navigateTo(`/${session.value.account.role.toLowerCase()}/tickets/${table.ticket.id}`))
    .catch(err => toast.error({ title: 'Error', message: err.message }))

const openTicket = (table) => navigateTo(`/${session.value.account.role.toLowerCase()}/tickets/${table.ticket.id}`)

const isMyTable = (table) => table.ticket.owner.id == session.value.id

const arrangedTables = computed(() => arrangeTables(currentFloor.value, tables.value ?? [], !isLgScreen.value))

watchEffect(() => {
    if (currentFloor.value == null) return
    size.value = {
        height: isLgScreen.value
            ? currentFloor.value.gridHeight
            : currentFloor.value.gridWidth,
        width: isLgScreen.value
            ? currentFloor.value.gridWidth
            : currentFloor.value.gridHeight,
    }
    refreshTables()
})

watchEffect(() => {
    if (floors.value?.length > 0 && !currentFloor.value)
        currentFloor.value = floors.value[0]
})
</script>

<template>
    <div class="card bg-base-200 h-full">
        <div class="card-body lg:flex-row">
            <div :class="['flex-none flex gap-1 overflow-auto',
                'lg:w-48 lg:flex-col'
            ]">
                <h3 class="not-lg:hidden block text-2xl">Pisos</h3>
                <div class="not-lg:hidden flex divider m-0"></div>
                <button :class="['btn lg:w-full', { 'btn-primary': floor.id == currentFloor?.id }]"
                    @click="currentFloor = floor" v-for="floor in floors" :key="floor.id">
                    {{ floor.name }}
                </button>
            </div>
            <div class="not-lg:hidden flex divider divider-horizontal m-0"></div>
            <div class="grow flex flex-col" v-if="!!currentFloor">
                <div class="grow grid place-items-center">
                    <div class="w-full grid gap-1" :style="{
                        'grid-template-columns': `repeat(${size.width}, 1fr)`,
                        'grid-template-rows': `repeat(${size.height}, 1fr)`,
                    }">
                        <div v-for="table in arrangedTables" class="contents">
                            <div v-if="!table" class="aspect-square bg-base-100 rounded-lg"></div>
                            <template v-else-if="table.status == 'AVAILABLE'">
                                <div @click="openTable(table)"
                                    class="grid place-content-center aspect-square bg-base-100 rounded-lg border border-success cursor-pointer">
                                    {{ table.name }}
                                </div>
                            </template>
                            <template v-else-if="table.status == 'OCCUPIED'">
                                <div @click="openTicket(table)"
                                    class="grid place-content-center aspect-square bg-base-100 rounded-lg border"
                                    :class="isMyTable(table) ? 'border-primary cursor-pointer' : 'border-error'">
                                    {{ table.name }}
                                </div>
                            </template>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>