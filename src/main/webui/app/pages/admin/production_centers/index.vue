<script setup>
import dayjs from 'dayjs';
import { deleteApiProductionCentersUuid, getApiProductionCenters } from '~/services/production-center-resource/production-center-resource';

definePageMeta({
    middleware: ['only-admin'],
})

const route = useRoute()
const toast = useToast()


const { params, setParam } = useParams('adminGetApiProductionCentersParams', { page: 1, id: route.query.id })

const { data: paginatedCenters, status, refresh } = useAsyncData('getApiProductionCenters', () => getApiProductionCenters(params.value), { default: () => [] })

const prevPage = _ => setParam('page', params.value.page - 1)
const nextPage = _ => setParam('page', params.value.page + 1)

const deleteCenter = (center) => deleteApiProductionCentersUuid(center.id)
    .then(_ => refresh())
    .then(_ => closeModal(`delete_pc_${center.id}_modal`))
    .then(_ => toast.info({ title: "Centro de producción eliminado" }))
    .catch(e => toast.error({ title: "Error", message: e.message }))
    .then(_ => closeModal(`delete_pc_${center.id}_modal`))

watch(params, _ => refresh())
</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Centros de producción">
            <div class="card bg-base-200 shadow-xl">
                <div class="card-body">
                    <div class="mb-3">
                        <NuxtLink to="/admin/production_centers/new" class="btn btn-primary flex-none">
                            Nuevo centro de producción
                        </NuxtLink>
                    </div>
                    <div class="overflow-x-auto rounded-box border border-base-content/5 bg-base-100">
                        <table class="table table-zebra">
                            <thead class="bg-base-200">
                                <tr>
                                    <th>#</th>
                                    <th>Nombre</th>
                                    <th>Descripción</th>
                                    <th>Estatus</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-if="status == 'pending'">
                                    <th colspan="8">
                                        <div class="grid place-items-center">
                                            <span class="loading loading-ring loading-xl"></span>
                                        </div>
                                    </th>
                                </tr>
                                <tr v-if="status == 'success'" v-for="center in paginatedCenters.data" :key="center.id">
                                    <td>
                                        <button class="btn btn-link" @click="copy(center.id, 'ID copiado')">
                                            {{ center.id.slice(0, 8) }}...
                                        </button>
                                    </td>
                                    <td>{{ center.name }}</td>
                                    <td>{{ center.description }}</td>
                                    <td>
                                        <div v-if="center.isActive" class="flex gap-2 items-center">
                                            <span class="indicator-item status status-success"></span>
                                            Habilitado
                                        </div>
                                        <div v-else class="flex gap-2 items-center">
                                            <span class="indicator-item status status-error"></span>
                                            Inhabilitado
                                        </div>
                                    </td>
                                    <td>
                                        <div class="tooltip" data-tip="Eliminar">
                                            <button class="btn btn-outline btn-sm btn-error"
                                                @click="openModal(`delete_pc_${center.id}_modal`)">
                                                <Icon name="material-symbols:delete-outline" class="text-xl" />
                                            </button>
                                        </div>
                                        <dialog :id="`delete_pc_${center.id}_modal`" class="modal">
                                            <div class="modal-box">
                                                <h3 class="text-lg font-bold">
                                                    ¿Eliminar centro de producción {{ center.name }}?
                                                </h3>
                                                <p class="py-4">
                                                    ID#{{ center.id }}
                                                </p>
                                                <div class="modal-action">
                                                    <button @click="deleteCenter(center)" class="btn btn-error">
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
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="grid place-items-center mt-4">
                        <div class="join" v-if="status == 'success'">
                            <button class="join-item btn" :class="{ 'btn-disabled': !paginatedCenters.meta.prevPage }"
                                @click="prevPage">«</button>
                            <button class="join-item btn btn-active cursor-auto">Page {{
                                paginatedCenters.meta.currentPage }}</button>
                            <button class="join-item btn" :class="{ 'btn-disabled': !paginatedCenters.meta.nextPage }"
                                @click="nextPage">»</button>
                        </div>
                    </div>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>