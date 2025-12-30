<script setup>
import { postApiFloors } from '~/services/floor-resource/floor-resource';

const toast = useToast()

const newFloor = (e) => {
    const gridHeight = +e.target.elements.namedItem('gridHeight').value
    const gridWidth = +e.target.elements.namedItem('gridWidth').value
    if (gridHeight > gridWidth) return toast.error({ title: "Error", message: "El alto de la cuadrícula no puede ser mayor que el ancho" })
    postApiFloors({
        gridHeight,
        gridWidth,
        levelOrder: +Date.now().toString().slice(-8),
        name: e.target.elements.namedItem('name').value
    })
        .then(_ => refreshNuxtData('getApiFloors'))
        .then(_ => e.target.reset())
        .then(_ => closeModal('create_new_table'))
        .then(_ => toast.success({ title: "Nueva mesa", message: "Creada con éxito" }))
        .catch(_ => toast.error({ title: "Error", message: "No se ha podido crear la mesa" }))
}
</script>

<template>
    <button class="btn btn-primary w-full" @click="openModal('create_new_table')">
        Crear nuevo piso
    </button>
    <dialog id="create_new_table" class="modal">
        <div class="modal-box">
            <h3 class="text-lg font-bold">Nuevo piso</h3>
            <form id="submit_new_floor" @submit.prevent="newFloor">
                <fieldset class="fieldset">
                    <legend class="fieldset-legend">Nombre</legend>
                    <input type="text" class="input w-full" name="name" />
                </fieldset>
                <div class="flex gap-1">
                    <fieldset class="flex-1 fieldset">
                        <legend class="fieldset-legend">Alto de cuadrícula</legend>
                        <input type="number" class="input w-full" name="gridHeight" />
                    </fieldset>
                    <fieldset class="flex-1 fieldset">
                        <legend class="fieldset-legend">Ancho de cuadrícula</legend>
                        <input type="number" class="input w-full" name="gridWidth" />
                    </fieldset>
                </div>
            </form>
            <div class="modal-action">
                <button class="btn btn-primary" type="submit" form="submit_new_floor">
                    Crear
                </button>
            </div>
        </div>
        <form method="dialog" class="modal-backdrop">
            <button>close</button>
        </form>
    </dialog>
</template>