<script setup>
import dayjs from 'dayjs'
import { deleteApiProductsUuid, getApiProducts } from '~/services/product-resource/product-resource'

definePageMeta({
    middleware: ['only-admin'],
})

const toast = useToast()

const currentPage = ref(1)

const { data: paginatedProducts, status, refresh } = useAsyncData('admin_products', () => getApiProducts({
    page: currentPage.value
}))


const nextPage = () => {
    if (paginatedProducts.value.meta.nextPage) {
        currentPage.value += 1
        refresh()
    }
}
const prevPage = () => {
    if (paginatedProducts.value.meta.prevPage) {
        currentPage.value -= 1
        refresh()
    }
}

const copyId = (text) =>
    navigator.clipboard.writeText(text)
        .then(_ => toast.info({ title: "ID Copiado" }))

const deleteProduct = (product) =>
    deleteApiProductsUuid(product.id)
        .then(() => {
            refresh()
            closeModal(`delete_${product.id}_modal`)
            toast.info({ title: "Producto eliminado" })
        })
        .catch(() => toast.error({ title: "Error al eliminar producto" }))
</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Productos">
            <div class="card bg-base-200 shadow-xl">
                <div class="card-body">
                    <div class="flex justify-end mb-3">
                        <NuxtLink to="/admin/products/new" class="btn btn-primary">
                            Nuevo Producto
                        </NuxtLink>
                    </div>
                    <div class="overflow-x-auto rounded-box border border-base-content/5 bg-base-100">
                        <table class="table table-zebra">
                            <thead class="bg-base-200">
                                <tr>
                                    <th>#</th>
                                    <th>Producto</th>
                                    <th>Detalle</th>
                                    <th>Precio</th>
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
                                <tr v-if="status == 'success'" v-for="product in paginatedProducts.data"
                                    :key="product.id">
                                    <td>
                                        <button class="btn btn-link" @click="copyId(product.id)">
                                            {{ product.id.slice(0, 8) }}...
                                        </button>
                                    </td>
                                    <td>{{ product.name }}</td>
                                    <td>
                                        <div class="tooltip" :data-tip="product.description">
                                            {{ product.description.slice(0, 9) }}...
                                        </div>
                                    </td>
                                    <td>${{ product.price }}</td>
                                    <td>{{ dayjs(product.availableFrom).format('DD/MM/YYYY')
                                        + ' - '
                                        + dayjs(product.availableUntil).format('DD/MM/YYYY') }}</td>
                                    <td>{{ product.availableFromTime.slice(0, 5)
                                        + ' - '
                                        + product.availableUntilTime.slice(0, 5)
                                        + ' hrs' }}</td>
                                    <td>
                                        <div class="badge badge-soft badge-primary">
                                            {{ shortenAvailableDays(product.availableDays) }}
                                        </div>
                                    </td>
                                    <td>
                                        <div v-if="product.isEnabled" class="flex gap-2 items-center">
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
                                            <div class="tooltip" data-tip="Categorias">
                                                <NuxtLink :to="`/admin/products/${product.id}/categories`"
                                                    class="btn btn-outline btn-sm btn-info">
                                                    <Icon name="material-symbols:category-rounded" class="text-xl" />
                                                </NuxtLink>
                                            </div>
                                            <div class="tooltip" data-tip="Editar">
                                                <button class="btn btn-outline btn-sm btn-secondary">
                                                    <Icon name="material-symbols:edit-outline" class="text-xl" />
                                                </button>
                                            </div>
                                            <div class="tooltip" data-tip="Eliminar">
                                                <button class="btn btn-outline btn-sm btn-error"
                                                    @click="openModal(`delete_${product.id}_modal`)">
                                                    <Icon name="material-symbols:delete-outline" class="text-xl" />
                                                </button>
                                            </div>
                                            <dialog :id="`delete_${product.id}_modal`" class="modal">
                                                <div class="modal-box">
                                                    <h3 class="text-lg font-bold">
                                                        ¿Eliminar producto {{ product.name }}?
                                                    </h3>
                                                    <p class="py-4">
                                                        ID#{{ product.id }}
                                                    </p>
                                                    <div class="modal-action">
                                                        <button @click="deleteProduct(product)" class="btn btn-error">
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
                            <button class="join-item btn" :class="{ 'btn-disabled': !paginatedProducts.meta.prevPage }"
                                @click="prevPage">«</button>
                            <button class="join-item btn btn-active cursor-auto">Page {{
                                paginatedProducts.meta.currentPage }}</button>
                            <button class="join-item btn" :class="{ 'btn-disabled': !paginatedProducts.meta.nextPage }"
                                @click="nextPage">»</button>
                        </div>
                    </div>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>