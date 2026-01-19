<script setup>
import { getApiIngredients } from '~/services/ingredient-resource/ingredient-resource';
import { getApiProducts } from '~/services/product-resource/product-resource';
import { postApiRecipes } from '~/services/recipe-resource/recipe-resource';

definePageMeta({
    middleware: ['only-admin'],
})

const toast = useToast()

const productSearch = ref('')
const selectedProduct = ref()

const {
    data: paginatedProducts,
    status: paginatedProductsStatus,
    refresh: refreshProducts,
} = useLazyAsyncData('admin_recipe_products', () => getApiProducts({ name: productSearch.value }),
    { default: () => [] })

const debounceRefreshProducts = debounce(() => refreshProducts(), 1000)

const selectProduct = (product) => Promise.resolve()
    .then(_ => document.activeElement.blur())
    .then(_ => selectedProduct.value = product)
    .then(_ => productSearch.value = product.name)

watchEffect(() => {
    if (productSearch.value == '') return
    debounceRefreshProducts()
})

const ingredientSearch = ref('')
const selectedIngredients = ref([])

const {
    data: paginatedIngredients,
    status: paginatedIngredientsStatus,
    refresh: refreshIngredients,
} = useLazyAsyncData('admin_recipe_ingredients', () => getApiIngredients({ name: ingredientSearch.value }),
    { default: () => [] })

const debounceRefreshIngredients = debounce(() => refreshIngredients(), 1000)

const addSelectedIngredient = (ingredient) => Promise.resolve()
    .then(_ => document.activeElement.blur())
    .then(_ => selectedIngredients.value)
    .then(c => c.findIndex(ing => ing.id == ingredient.id))
    .then(index => index != -1 && (() => { throw new Error('Ya esta agregada ese ingrediente') })())
    .then(_ => selectedIngredients.value.push(ingredient))
    .then(_ => ingredientSearch.value = selectedIngredients.value.map(c => c.name).join(', '))
    .catch(e => toast.error({ title: e.message }))

const removeSelectedIngredient = (ingredient) => Promise.resolve()
    .then(_ => document.activeElement.blur())
    .then(_ => selectedIngredients.value)
    .then(c => c.findIndex(ing => ing.id == ingredient.id))
    .then(index => index != -1 ? index : (() => { throw new Error('No se encontro el ingrediente') })())
    .then(index => selectedIngredients.value.splice(index, 1))
    .catch(e => toast.error({ title: e.message }))

watchEffect(() => {
    if (ingredientSearch.value == '') return
    debounceRefreshIngredients()
})

const onSubmitNewItem = (e) => postApiRecipes({
    name: e.target.name.value,
    description: e.target.description.value,
    productId: selectedProduct.value.id,
    ingredientIds: selectedIngredients.value.map(c => c.id),
})
    .then(_ => toast.success({ message: 'Receta creada con exito' }))
    .then(_ => navigateTo('/admin/recipes'))
    .catch(_ => toast.error({ message: 'Error al crear la Receta' }))
</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Nuevo artículo">
            <div class="card bg-base-200 shadow-xl">
                <div class="card-body">
                    <form @submit.prevent="onSubmitNewItem" class="grid grid-cols-1 lg:grid-cols-2 gap-2">
                        <fieldset class="fieldset lg:col-span-2">
                            <legend class="fieldset-legend">Receta</legend>
                            <input type="text" class="input w-full" name="name" />
                        </fieldset>
                        <fieldset class="fieldset lg:col-span-2">
                            <legend class="fieldset-legend">Descripción</legend>
                            <textarea class="textarea w-full" name="description"></textarea>
                        </fieldset>
                        <div class="dropdown lg:col-span-2">
                            <fieldset class="fieldset">
                                <legend class="fieldset-legend">Product</legend>
                                <input v-model="productSearch" type="text" class="input w-full" />
                            </fieldset>
                            <ul tabindex="-1"
                                class="dropdown-content menu bg-base-100 rounded-box z-1 w-full p-2 shadow-sm">
                                <li v-if="paginatedProductsStatus == 'pending'">
                                    <div class="grid place-items-center">
                                        <span class="loading loading-ring loading-xl"></span>
                                    </div>
                                </li>
                                <template v-if="paginatedProductsStatus == 'success'">
                                    <li v-for="product in paginatedProducts?.data" :key="product.id">
                                        <a @click="selectProduct(product)"
                                            :class="{ 'bg-primary': product.id == selectedProduct?.id }">
                                            {{ product.name }}
                                        </a>
                                    </li>
                                </template>
                            </ul>
                        </div>
                        <div class="dropdown lg:col-span-2">
                            <fieldset class="fieldset">
                                <legend class="fieldset-legend">Ingredientes</legend>
                                <input @focus="ingredientSearch = ''"
                                    @blur="ingredientSearch = selectedIngredients.map(c => c.name).join(', ')"
                                    v-model="ingredientSearch" type="text" class="input w-full" />
                            </fieldset>
                            <ul tabindex="-1"
                                class="dropdown-content menu bg-base-100 rounded-box z-1 w-full p-2 shadow-sm">
                                <li v-if="paginatedIngredientsStatus == 'pending'">
                                    <div class="grid place-items-center">
                                        <span class="loading loading-ring loading-xl"></span>
                                    </div>
                                </li>
                                <template v-if="paginatedIngredientsStatus == 'success'">
                                    <li v-for="ingredient in paginatedIngredients?.data" :key="ingredient.id">
                                        <div class="hidden">{{selected = selectedIngredients.map(c =>
                                            c.id).includes(ingredient.id)}}</div>
                                        <a @click="selected ? removeSelectedIngredient(ingredient) : addSelectedIngredient(ingredient)"
                                            :class="{ 'bg-primary': selected }">
                                            {{ ingredient.name }}
                                        </a>
                                    </li>
                                </template>
                            </ul>
                        </div>
                        <div class="lg:col-span-2 flex flex-wrap gap-2">
                            <button v-for="ingredient in selectedIngredients" :key="ingredient.id"
                                @click="removeSelectedIngredient(ingredient)" :class="[
                                    'group btn btn-outline btn-primary items-center',
                                    'hover:btn-error'
                                ]">
                                <div class="hidden group-hover:flex justify-center">
                                    <Icon name="material-symbols:cancel-outline" class="text-2xl" />
                                </div>
                                {{ ingredient.name }}
                            </button>
                        </div>
                        <div class="lg:col-span-2 lg:grid lg:justify-end mt-3">
                            <button class="w-full btn btn-primary lg:w-60">Crear</button>
                        </div>
                    </form>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>