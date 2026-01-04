<script setup>
import { deleteApiCategoriesCategoryIdProductsProductId, getApiCategoriesCategoryIdProducts, postApiCategoriesCategoryIdProductsProductId } from '~/services/category-product-resouce/category-product-resouce';
import { getApiCategoriesUuid } from '~/services/category-resource/category-resource';
import { getApiProducts } from '~/services/product-resource/product-resource';

definePageMeta({
    middleware: ['only-admin'],
})

const toast = useToast()
const route = useRoute()
const { params, setParam } = useParams('adminGetApiProductsInCategories', { size: 100 })
const categoryId = route.params.id

const title = ref('Productos en categoria')

const { data: category, status } =
    useLazyAsyncData(`getApiCategoriesUuid-${categoryId}`, () => getApiCategoriesUuid(categoryId))

const { data: categoryProducts, refresh: refreshCategoryProducts } =
    useLazyAsyncData(`getApiCategoriesCategoryIdProducts-${categoryId}`, () => getApiCategoriesCategoryIdProducts(categoryId), {
        immediate: false,
        default: () => []
    })

const { data: products, status: productsStatus, refresh: refreshProducts } =
    useLazyAsyncData('getApiProducts', () => getApiProducts(params.value), {
        immediate: false,
        default: () => []
    })

const addCategory = (product) => postApiCategoriesCategoryIdProductsProductId(categoryId, product.id)
    .then(_ => toast.info({ message: 'Producto agregado a la categoria' }))
    .then(_ => refreshCategoryProducts())
    .catch(_ => toast.error({ message: 'Error al agregar el producto a la categoria' }))

const removeCategory = (product) => deleteApiCategoriesCategoryIdProductsProductId(categoryId, product.id)
    .then(_ => toast.info({ message: 'Producto eliminado de la categoria' }))
    .then(_ => refreshCategoryProducts())
    .catch(_ => toast.error({ message: 'Error al eliminar el producto de la categoria' }))

const onSubmitSearch = (e) => setParam('name', e.target.name.value)

const isProductInCategory = (product) =>
    categoryProducts.value.some(categoryProduct => categoryProduct.id == product.id)

watch(params, () => refreshProducts())

watch(status, () => {
    if (status.value == 'error') {
        toast.error({ message: 'Error al cargar la categoria' })
        return navigateTo('/admin/categories')
    }
    if (status.value == 'success') {
        title.value = `Productos en categoria ${category.value.name}`
        refreshProducts()
        refreshCategoryProducts()
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
                        <h3 class="card-title">Productos</h3>
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
                        <div v-if="productsStatus == 'pending'" class="grid place-items-center">
                            <span class="loading loading-ring loading-xl"></span>
                        </div>
                        <div v-if="productsStatus == 'success'"
                            class="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-5 xl:grid-cols-8 gap-4">
                            <template v-for="product in
                                products.data" :key="product.id">
                                <button v-if="isProductInCategory(product)"
                                    class="aspect-square grid place-content-center rounded-lg bg-primary cursor-pointer"
                                    @click="removeCategory(product)">
                                    {{ product.name }}
                                </button>
                                <button v-else
                                    class="aspect-square grid place-content-center rounded-lg bg-base-100 cursor-pointer"
                                    @click="addCategory(product)">
                                    {{ product.name }}
                                </button>
                            </template>
                        </div>
                    </div>
                </div>
            </template>
        </NuxtLayout>
    </div>
</template>