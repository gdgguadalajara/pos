<script setup>
import { deleteApiCategoriesCategoryIdProductsProductId, getApiCategoriesCategoryIdProducts, postApiCategoriesCategoryIdProductsProductId } from '~/services/category-product-resouce/category-product-resouce';
import { getApiCategoriesUuid } from '~/services/category-resource/category-resource';
import { getApiProducts } from '~/services/product-resource/product-resource';

definePageMeta({
    middleware: ['only-admin'],
})

const toast = useToast()
const route = useRoute()
const categoryId = route.params.id

const title = ref('Productos en categoria')
const search = ref('')

const { data: category, status } =
    useLazyAsyncData(categoryId, () => getApiCategoriesUuid(categoryId))

const { data: categoryProducts, refresh: refreshCategoryProducts } =
    useLazyAsyncData(`${categoryId}-products`, () => getApiCategoriesCategoryIdProducts(categoryId), {
        immediate: false,
        default: () => []
    })

const { data: products, status: productsStatus, refresh: refreshProducts } =
    useLazyAsyncData('products', () => getApiProducts({
        size: 100
    }), {
        immediate: false,
        default: () => []
    })

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

const addCategory = (product) => postApiCategoriesCategoryIdProductsProductId(categoryId, product.id)
    .then(() => {
        toast.info({ message: 'Producto agregado a la categoria' })
        refreshCategoryProducts()
    })
    .catch(() => toast.error({ message: 'Error al agregar el producto a la categoria' }))

const removeCategory = (product) => deleteApiCategoriesCategoryIdProductsProductId(categoryId, product.id)
    .then(() => {
        toast.info({ message: 'Producto eliminado de la categoria' })
        refreshCategoryProducts()
    })
    .catch(() => {
        toast.error({ message: 'Error al eliminar el producto de la categoria' })
    })

const isProductInCategory = (product) => {
    return categoryProducts.value.some(categoryProduct => categoryProduct.id == product.id)
}
</script>

<template>
    <div>
        <NuxtLayout name="admin" :title="title">
            <template v-if="status == 'pending' || productsStatus == 'pending'">
                <div class="grid place-items-center">
                    <span class="loading loading-ring loading-xl"></span>
                </div>
            </template>
            <template v-if="status == 'success' && productsStatus == 'success'">
                <div class="card bg-base-200">
                    <div class="card-body">
                        <h3 class="card-title">Productos</h3>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Buscar</legend>
                            <input v-model="search" type="text" class="input w-full"
                                placeholder="🔍 Buscar por nombre" />
                        </fieldset>
                        <div class="divider"></div>
                        <div class="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-5 xl:grid-cols-8 gap-4">
                            <template
                                v-for="product in
                                    products.data.filter(product => product.name.toLowerCase().startsWith(search.toLowerCase()))"
                                :key="product.id">
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