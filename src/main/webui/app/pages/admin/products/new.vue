<script setup>
import dayjs from 'dayjs'
import { DayOfWeek } from '~/models'
import { postApiCategoriesCategoryIdProductsProductId } from '~/services/category-product-resouce/category-product-resouce'
import { getApiCategories } from '~/services/category-resource/category-resource'
import { postApiProducts } from '~/services/product-resource/product-resource'

definePageMeta({
    middleware: ['only-admin'],
})

const toast = useToast()

const selectedCategories = ref([])
const categorySearch = ref('')

const addSelectedCategory = (category) => Promise.resolve()
    .then(_ => document.activeElement.blur())
    .then(_ => selectedCategories.value)
    .then(c => c.findIndex(cat => cat.id == category.id))
    .then(index => index != -1 && (() => { throw new Error('Ya esta agregada esa categoria') })())
    .then(_ => selectedCategories.value.push(category))
    .catch(e => toast.error({ title: e.message }))

const removeSelectedCategory = (category) => Promise.resolve()
    .then(_ => document.activeElement.blur())
    .then(_ => selectedCategories.value)
    .then(c => c.findIndex(cat => cat.id == category.id))
    .then(index => index != -1 ? index : (() => { throw new Error('No se encontro la categoria') })())
    .then(index => selectedCategories.value.splice(index, 1))
    .catch(e => toast.error({ title: e.message }))

const {
    data: categories,
    refresh: refreshCategories,
    status: categoriesStatus
} = useAsyncData(() => getApiCategories({ name: categorySearch.value }),
    { immediate: false, default: () => [] })

const debounceRefreshCategories = debounce(() => refreshCategories(), 1000)

watchEffect(() => {
    if (categorySearch.value == '') return
    debounceRefreshCategories()
})

const onSubmitCreateProduct = (e) => {
    const availableDays = []
    if (e.target.monday.checked) availableDays.push(DayOfWeek.MONDAY)
    if (e.target.tuesday.checked) availableDays.push(DayOfWeek.TUESDAY)
    if (e.target.wednesday.checked) availableDays.push(DayOfWeek.WEDNESDAY)
    if (e.target.thursday.checked) availableDays.push(DayOfWeek.THURSDAY)
    if (e.target.friday.checked) availableDays.push(DayOfWeek.FRIDAY)
    if (e.target.saturday.checked) availableDays.push(DayOfWeek.SATURDAY)
    if (e.target.sunday.checked) availableDays.push(DayOfWeek.SUNDAY)
    const payload = {
        name: e.target.name.value,
        description: e.target.description.value,
        price: +e.target.price.value,
        isEnabled: e.target.isEnabled.checked,
        availableFrom: dayjs(e.target.available_from.value).toISOString(),
        availableUntil: dayjs(e.target.available_until.value).toISOString(),
        availableFromTime: e.target.available_from_hour.value,
        availableUntilTime: e.target.available_until_hour.value,
        availableDays: availableDays
    }
    postApiProducts(payload)
        .then(product => Promise.resolve()
            .then(_ => e.target.reset())
            .then(_ => selectedCategories.value.length > 0)
            .then(hasItems => hasItems && selectedCategories.value.forEach(category =>
                postApiCategoriesCategoryIdProductsProductId(category.id, product.id)))
            .then(_ => toast.success({ title: "Producto creado" }))
            .then(_ => navigateTo("/admin/products"))
        )
        .catch(() => toast.error({ title: "Error al crear producto" }))
}
</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Nuevo Product">
            <div class="card bg-base-200 shadow-xl">
                <div class="card-body">
                    <form @submit.prevent="onSubmitCreateProduct" class="grid grid-cols-1 lg:grid-cols-2 gap-2">
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Producto</legend>
                            <input type="text" class="input w-full" name="name" />
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Precio</legend>
                            <input type="number" inputmode="numeric" name="price" class="input input-bordered w-full" />
                        </fieldset>
                        <fieldset class="fieldset lg:col-span-2">
                            <legend class="fieldset-legend">Descripción</legend>
                            <textarea class="textarea w-full" name="description"></textarea>
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Disponible desde</legend>
                            <input :value="dayjs().format('YYYY-MM-DD')" type="date" name="available_from"
                                class="input w-full" />
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Disponible hasta</legend>
                            <input :value="dayjs().add(1, 'year').format('YYYY-MM-DD')" type="date"
                                name="available_until" class="input w-full" />
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Disponible desde la hora</legend>
                            <input value="00:00" type="time" name="available_from_hour" class="input w-full" />
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Disponible desde la hora</legend>
                            <input value="23:59" type="time" name="available_until_hour" class="input w-full" />
                        </fieldset>
                        <h1 class="font-bold text-gl lg:col-span-2">Dias disponible:</h1>
                        <div class="lg:col-span-2">
                            <div class="grid grid-cols-2 lg:grid-cols-4 xl:grid-cols-7 gap-3">
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-lg">
                                        <input name="monday" type="checkbox" class="checkbox" checked />
                                        Lunes
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-lg">
                                        <input name="tuesday" type="checkbox" class="checkbox" checked />
                                        Martes
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-lg">
                                        <input name="wednesday" type="checkbox" class="checkbox" checked />
                                        Miercoles
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-lg">
                                        <input name="thursday" type="checkbox" class="checkbox" checked />
                                        Jueves
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-lg">
                                        <input name="friday" type="checkbox" class="checkbox" checked />
                                        Viernes
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-lg">
                                        <input name="saturday" type="checkbox" class="checkbox" checked />
                                        Sabado
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-lg">
                                        <input name="sunday" type="checkbox" class="checkbox" checked />
                                        Domingo
                                    </label>
                                </fieldset>
                            </div>
                        </div>
                        <div class="dropdown lg:col-span-2">
                            <fieldset class="fieldset">
                                <legend class="fieldset-legend">Categorias</legend>
                                <input v-model="categorySearch" type="text" class="input w-full" />
                            </fieldset>
                            <ul tabindex="-1"
                                class="dropdown-content menu bg-base-100 rounded-box z-1 w-full p-2 shadow-sm">
                                <li v-if="categoriesStatus == 'pending'">
                                    <div class="grid place-items-center">
                                        <span class="loading loading-ring loading-xl"></span>
                                    </div>
                                </li>
                                <template v-if="categoriesStatus == 'success'">
                                    <li v-for="category in categories?.data" :key="category.id">
                                        <div class="hidden">{{selected = selectedCategories.map(c =>
                                            c.id).includes(category.id)}}</div>
                                        <a @click="selected ? removeSelectedCategory(category) : addSelectedCategory(category)"
                                            :class="{ 'bg-primary': selected }">
                                            {{ category.name }}
                                        </a>
                                    </li>
                                </template>
                            </ul>
                        </div>
                        <div class="lg:col-span-2 flex flex-wrap gap-2">
                            <button v-for="category in selectedCategories" :key="category.id"
                                @click="removeSelectedCategory(category)" :class="[
                                    'group btn btn-outline btn-primary items-center',
                                    'hover:btn-error'
                                ]">
                                <div class="hidden group-hover:flex justify-center">
                                    <Icon name="material-symbols:cancel-outline" class="text-2xl" />
                                </div>
                                {{ category.name }}
                            </button>
                        </div>
                        <fieldset class="fieldset py-2">
                            <legend class="fieldset-legend">Opciones</legend>
                            <label class="label">
                                <input type="checkbox" checked class="toggle toggle-success" name="isEnabled" />
                                Habilidado
                            </label>
                        </fieldset>
                        <div class="lg:col-span-2 lg:grid lg:justify-end mt-3">
                            <button class="w-full btn btn-primary lg:w-60">Crear nuevo producto</button>
                        </div>
                    </form>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>