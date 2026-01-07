<script setup>
import dayjs from 'dayjs';
import { deleteApiCategoriesUuid, getApiCategories } from '~/services/category-resource/category-resource';

definePageMeta({
    middleware: ['only-admin'],
})

const toast = useToast()
const { params, setParam } = useParams('adminGetApiCategoriesParams', { page: 1, sort: 'name' })

const { data: paginatedCategories, status, refresh } = useLazyAsyncData('admin_categories', () => getApiCategories(params.value))

const deleteCategory = (category) =>
    deleteApiCategoriesUuid(category.id)
        .then(_ => refresh())
        .then(_ => closeModal(`delete_${category.id}_modal`))
        .then(_ => toast.info({ title: "Categoria eliminada" }))
        .catch(_ => toast.error({ title: "Error al eliminar categoria" }))

const prevPage = _ => setParam('page', params.value.page - 1)
const nextPage = _ => setParam('page', params.value.page + 1)

watch(params, _ => refresh())
</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Categorias">
            <div class="card bg-base-200 shadow-xl">
                <div class="card-body">
                    <div class="mb-3">
                        <NuxtLink to="/admin/categories/new" class="btn btn-primary flex-none">
                            Nueva Categoria
                        </NuxtLink>
                    </div>
                    <AdminCategoriesFilters />
                    <div class="overflow-x-auto rounded-box border border-base-content/5 bg-base-100">
                        <table class="table table-zebra">
                            <thead class="bg-base-200">
                                <tr>
                                    <th>#</th>
                                    <th>Categoría</th>
                                    <th>Detalle</th>
                                    <th>Vigencia</th>
                                    <th>Horario</th>
                                    <th>Días</th>
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
                                <tr v-if="status == 'success'" v-for="category in paginatedCategories.data"
                                    :key="category.id">
                                    <td>
                                        <button class="btn btn-link" @click="copy(category.id, 'ID copiado')">
                                            {{ category.id.slice(0, 8) }}...
                                        </button>
                                    </td>
                                    <td>{{ category.name }}</td>
                                    <td>
                                        <div class="tooltip" :data-tip="category.description">
                                            {{ category.description.slice(0, 9) }}...
                                        </div>
                                    </td>
                                    <td>{{ dayjs(category.availableFrom).format('DD/MM/YYYY')
                                        + ' - '
                                        + dayjs(category.availableUntil).format('DD/MM/YYYY') }}</td>
                                    <td>{{ category.availableFromTime.slice(0, 5)
                                        + ' - '
                                        + category.availableUntilTime.slice(0, 5)
                                        + ' hrs' }}</td>
                                    <td>
                                        <div class="badge badge-soft badge-primary">
                                            {{ shortenAvailableDays(category.availableDays) }}
                                        </div>
                                    </td>
                                    <td>
                                        <div v-if="category.isEnabled" class="flex gap-2 items-center">
                                            <span class="indicator-item status status-success"></span>
                                            Habilitado
                                        </div>
                                        <div v-else class="flex gap-2 items-center">
                                            <span class="indicator-item status status-error"></span>
                                            Inhabilitado
                                        </div>
                                    </td>
                                    <td>
                                        <div class="flex gap-1">
                                            <div class="tooltip" data-tip="Productos">
                                                <NuxtLink :to="`/admin/categories/${category.id}/products`"
                                                    class="btn btn-outline btn-sm btn-info">
                                                    <Icon name="material-symbols:fastfood" class="text-xl" />
                                                </NuxtLink>
                                            </div>
                                            <div class="tooltip" data-tip="Editar">
                                                <button class="btn btn-outline btn-sm btn-secondary">
                                                    <Icon name="material-symbols:edit-outline" class="text-xl" />
                                                </button>
                                            </div>
                                            <div class="tooltip" data-tip="Eliminar">
                                                <button class="btn btn-outline btn-sm btn-error"
                                                    @click="openModal(`delete_${category.id}_modal`)">
                                                    <Icon name="material-symbols:delete-outline" class="text-xl" />
                                                </button>
                                            </div>
                                            <dialog :id="`delete_${category.id}_modal`" class="modal">
                                                <div class="modal-box">
                                                    <h3 class="text-lg font-bold">
                                                        ¿Eliminar categoria {{ category.name }}?
                                                    </h3>
                                                    <p class="py-4">
                                                        ID#{{ category.id }}
                                                    </p>
                                                    <div class="modal-action">
                                                        <button @click="deleteCategory(category)" class="btn btn-error">
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
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="grid place-items-center mt-4">
                        <div class="join" v-if="status == 'success'">
                            <button class="join-item btn"
                                :class="{ 'btn-disabled': !paginatedCategories.meta.prevPage }"
                                @click="prevPage">«</button>
                            <button class="join-item btn btn-active cursor-auto">Page {{
                                paginatedCategories.meta.currentPage }}</button>
                            <button class="join-item btn"
                                :class="{ 'btn-disabled': !paginatedCategories.meta.nextPage }"
                                @click="nextPage">»</button>
                        </div>
                    </div>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>