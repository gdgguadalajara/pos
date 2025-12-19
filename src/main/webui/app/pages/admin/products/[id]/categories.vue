<script setup>
import { getApiCategories } from '~/services/category-resource/category-resource'
import { deleteApiProductsProductIdCategoriesCategoryId, getApiProductsProductIdCategories, postApiProductsProductIdCategoriesCategoryId } from '~/services/product-category-resource/product-category-resource'
import { getApiProductsUuid } from '~/services/product-resource/product-resource'

definePageMeta({
    middleware: ['only-admin'],
})

const toast = useToast()
const route = useRoute()
const productId = route.params.id

const title = ref('Categorias en producto')
const search = ref('')

const { data: product, status } =
    useLazyAsyncData(productId, () => getApiProductsUuid(productId))

const { data: productCategories, refresh: refreshProductCategories } =
    useLazyAsyncData(`${productId}-categories`, () => getApiProductsProductIdCategories(productId), {
        immediate: false,
        default: () => []
    })

const { data: categories, status: categoriesStatus, refresh: refreshCategories } =
    useLazyAsyncData('categories', () => getApiCategories({ size: 100 }), {
        immediate: false,
        default: () => []
    })

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

const addProduct = (category) => postApiProductsProductIdCategoriesCategoryId(productId, category.id)
    .then(() => {
        toast.info({ message: 'Categoria agregada al producto' })
        refreshProductCategories()
    })
    .catch(() => toast.error({ message: 'Error al agregar la categoria al producto' }))

const removeProduct = (category) => deleteApiProductsProductIdCategoriesCategoryId(productId, category.id)
    .then(() => {
        toast.info({ message: 'Categoria eliminada del producto' })
        refreshProductCategories()
    })
    .catch(() => {
        toast.error({ message: 'Error al eliminar la categoria del producto' })
    })

const isCategoryInProduct = (category) => {
    return productCategories.value.some(productCategory => productCategory.id == category.id)
}
</script>

<template>
    <div>
        <NuxtLayout name="admin" :title="title">
            <template v-if="status == 'pending' || categoriesStatus == 'pending'">
                <div class="grid place-items-center">
                    <span class="loading loading-ring loading-xl"></span>
                </div>
            </template>
            <template v-if="status == 'success' && categoriesStatus == 'success'">
                <div class="card bg-base-200">
                    <div class="card-body">
                        <h3 class="card-title">Categorias</h3>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Buscar</legend>
                            <input v-model="search" type="text" class="input w-full"
                                placeholder="🔍 Buscar por nombre" />
                        </fieldset>
                        <div class="divider"></div>
                        <div class="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-5 xl:grid-cols-8 gap-4">
                            <template
                                v-for="category in
                                    categories.data.filter(category => category.name.toLowerCase().startsWith(search.toLowerCase()))"
                                :key="category.id">
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