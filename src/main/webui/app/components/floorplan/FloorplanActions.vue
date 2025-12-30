<script setup>
import { getApiFloorsFloorIdTables, postApiFloorsFloorIdTables } from '~/services/floor-resource/floor-resource'

const toast = useToast()
const currentFloor = useState('admin-current-floor')

const { data: tables } = useAsyncData(`getApiFloorsFloorIdTables-${currentFloor.value.id}`, () => getApiFloorsFloorIdTables(currentFloor.value.id))

const createTable = (e) => {
    const { x, y } = getNextTableAvailableInTheMatrix()
    postApiFloorsFloorIdTables(currentFloor.value.id, {
        name: e.target.name.value,
        posX: x,
        posY: y
    })
        .then(_ => refreshNuxtData(`getApiFloorsFloorIdTables-${currentFloor.value.id}`))
        .then(_ => closeModal(`create_table_${currentFloor.value.id}_modal`))
        .then(_ => toast.success({ title: "Nueva mesa", message: "Creada con éxito" }))
        .catch(_ => toast.error({ title: "Error", message: "No se ha podido crear la mesa" }))
}

const getNextTableAvailableInTheMatrix = () => {
    const width = currentFloor.value.gridWidth
    const height = currentFloor.value.gridHeight
    for (let i = 0; i < height; i++) {
        for (let j = 0; j < width; j++) {
            const tableAtPosition = tables.value.find(table => table.posX === j && table.posY === i)
            if (!tableAtPosition) return { x: j, y: i }
        }
    }
}
</script>

<template>
    <div>
        <div class="tooltip w-full" data-tip="Crear mesa">
            <button @click="openModal(`create_table_${currentFloor.id}_modal`)" class="btn w-full">
                <Icon name="material-symbols:table-bar" class="text-2xl" />
            </button>
        </div>
        <dialog :id="`create_table_${currentFloor.id}_modal`" class="modal">
            <div class="modal-box">
                <form id="create_table_form" @submit.prevent="createTable">
                    <fieldset class="fieldset">
                        <legend class="fieldset-legend">Nombre</legend>
                        <input type="text" class="input w-full" name="name" />
                    </fieldset>
                </form>
                <div class="modal-action">
                    <button type="submit" form="create_table_form" class="btn btn-primary">
                        Crear Mesa
                    </button>
                    <form method="dialog">
                        <button class="btn">Cancelar</button>
                    </form>
                </div>
            </div>
            <form method="dialog" class="modal-backdrop">
                <button>close</button>
            </form>
        </dialog>
    </div>
</template>
