<script setup>
import { getApiCategories } from '~/services/category-resource/category-resource'
import { deleteApiProductsProductIdCategoriesCategoryId, getApiProductsProductIdCategories, postApiProductsProductIdCategoriesCategoryId } from '~/services/product-category-resource/product-category-resource'
import { getApiProductsUuid } from '~/services/product-resource/product-resource'

definePageMeta({
    middleware: ['only-admin'],
})

const toast = useToast()
const route = useRoute()
const { params, setParam } = useParams('adminGetApiProductsInCategories', { size: 100 })
const productId = route.params.id

const title = ref('Categorias en producto')

const { data: product, status } =
    useLazyAsyncData(`getApiProductsUuid-${productId}`, () => getApiProductsUuid(productId))

const { data: productCategories, refresh: refreshProductCategories } =
    useLazyAsyncData(`getApiProductsProductIdCategories-${productId}`, () => getApiProductsProductIdCategories(productId), {
        immediate: false,
        default: () => []
    })

const { data: categories, status: categoriesStatus, refresh: refreshCategories } =
    useLazyAsyncData('getApiCategories', () => getApiCategories(params.value), {
        immediate: false,
        default: () => []
    })

const onSubmitSearch = (e) => setParam('name', e.target.name.value)

const addProduct = (category) => postApiProductsProductIdCategoriesCategoryId(productId, category.id)
    .then(() => toast.info({ message: 'Categoria agregada al producto' }))
    .then(_ => refreshProductCategories())
    .catch(_ => toast.error({ message: 'Error al agregar la categoria al producto' }))

const removeProduct = (category) => deleteApiProductsProductIdCategoriesCategoryId(productId, category.id)
    .then(_ => toast.info({ message: 'Categoria eliminada del producto' }))
    .then(_ => refreshProductCategories())
    .catch(_ => toast.error({ message: 'Error al eliminar la categoria del producto' }))

const isCategoryInProduct = (category) =>
    productCategories.value.some(productCategory => productCategory.id == category.id)

watch(params, () => refreshCategories())

watch(status, () => {
    if (status.value == 'error') {
        toast.error({ message: 'Error al cargar la producto' })
        return navigateTo('/admin/products')
    }
    if (status.value == 'success') {
        title.value = `Categorias en producto ${product.value.name}`
        refreshCategories()
        refreshProductCategories()
    }
})
</script>

<template>
    <div>
        <NuxtLayout name="admin" :title="title">
            <template v-if="status == 'pending'">
                <div class="grid place-items-center">
                    <span class="loading loading-ring loading-xl"></span>
                </div>
            </template>
            <template v-if="status == 'success'">
                <div class="card bg-base-200">
                    <div class="card-body">
                        <h3 class="card-title">Categorias</h3>
                        <form class="flex items-end gap-2 mb-3" @submit.prevent="onSubmitSearch">
                            <fieldset class="fieldset flex-1 p-0">
                                <legend class="fieldset-legend">Buscar por nombre</legend>
                                <input type="text" class="input w-full" name="name"
                                    placeholder="🔍 Buscar por nombre" />
                            </fieldset>
                            <button class="btn btn-primary flex-none px-10">
                                <Icon name="material-symbols:search" class="text-xl" />
                                Buscar
                            </button>
                        </form>
                        <div class="divider"></div>
                        <template v-if="categoriesStatus == 'pending'">
                            <div class="grid place-items-center">
                                <span class="loading loading-ring loading-xl"></span>
                            </div>
                        </template>
                        <div v-if="categoriesStatus == 'success'"
                            class="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-5 xl:grid-cols-8 gap-4">
                            <template v-for="category in
                                categories.data" :key="category.id">
                                <button v-if="isCategoryInProduct(category)"
                                    class="aspect-square grid place-content-center rounded-lg bg-primary cursor-pointer"
                                    @click="removeProduct(category)">
                                    {{ category.name }}
                                </button>
                                <button v-else
                                    class="aspect-square grid place-content-center rounded-lg bg-base-100 cursor-pointer"
                                    @click="addProduct(category)">
                                    {{ category.name }}
                                </button>
                            </template>
                        </div>
                    </div>
                </div>
            </template>
        </NuxtLayout>
    </div>
</template>