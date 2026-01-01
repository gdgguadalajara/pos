<script setup>
import { deleteApiFloorsUuid } from '~/services/floor-resource/floor-resource';

const toast = useToast()
const currentFloor = useState('admin-current-floor')

const deleteCurrentFloor = () => deleteApiFloorsUuid(currentFloor.value.id)
    .then(_ => refreshNuxtData('getApiFloors'))
    .then(_ => closeModal(`delete_${currentFloor.value.id}_modal`))
    .then(_ => toast.info({ title: "Piso eliminado", message: "Eliminado con éxito" }))
    .then(_ => currentFloor.value = null)
</script>

<template>
    <div class="tooltip" data-tip="Eliminar">
        <button class="btn btn-outline btn-sm btn-error" @click="openModal(`delete_${currentFloor.id}_modal`)">
            <Icon name="material-symbols:delete-outline" class="text-xl" />
        </button>
    </div>
    <dialog :id="`delete_${currentFloor.id}_modal`" class="modal">
        <div class="modal-box">
            <h3 class="text-lg font-bold">
                ¿Eliminar piso {{ currentFloor.name }}?
            </h3>
            <p class="py-4">
                ID#{{ currentFloor.id }}
            </p>
            <div class="modal-action">
                <button @click="deleteCurrentFloor()" class="btn btn-error">
                    Eliminar
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
</template>
