<script setup>
import dayjs from 'dayjs'
import { DayOfWeek } from '~/models'
import { postApiCategoriesCategoryIdProductsProductId } from '~/services/category-product-resouce/category-product-resouce'
import { getApiCategories } from '~/services/category-resource/category-resource'
import { getApiProductsUuid, postApiProducts, putApiProductsUuid } from '~/services/product-resource/product-resource'

definePageMeta({
    middleware: ['only-admin'],
})

const toast = useToast()
const route = useRoute()
const productId = route.params.id

const title = ref('Actualizar Producto')

const { data: product, status: productStatus } = useLazyAsyncData(() =>
    getApiProductsUuid(productId))

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
    putApiProductsUuid(productId, payload)
        .then(_ => e.target.reset())
        .then(_ => toast.success({ title: "Producto actualizado" }))
        .then(_ => navigateTo("/admin/products"))
        .catch(() => toast.error({ title: "Error al actualizar producto" }))
}

watchEffect(() => {
    if (product.value != null)
        title.value = `Acualizar producto: ${product.value.name}`
})
watchEffect(() => {
    if (productStatus.value == 'error')
        navigateTo("/admin/products")
            .then(_ => toast.error({ title: 'Error al cargar producto' }))
})
</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Nuevo Product">
            <div class="card bg-base-200 shadow-xl">
                <div class="card-body">
                    <div v-if="productStatus != 'success'" class="grid place-items-center">
                        <span class="loading loading-ring loading-xl"></span>
                    </div>
                    <form v-else @submit.prevent="onSubmitCreateProduct" class="grid grid-cols-1 lg:grid-cols-2 gap-2">
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Producto</legend>
                            <input type="text" class="input w-full" name="name" :value="product.name" />
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Precio</legend>
                            <input type="number" inputmode="numeric" name="price" class="input input-bordered w-full"
                                :value="product.price" />
                        </fieldset>
                        <fieldset class="fieldset lg:col-span-2">
                            <legend class="fieldset-legend">Descripción</legend>
                            <textarea class="textarea w-full" name="description"
                                :value="product.description"></textarea>
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Disponible desde</legend>
                            <input :value="dayjs(product.availableFrom).format('YYYY-MM-DD')" type="date"
                                name="available_from" class="input w-full" />
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Disponible hasta</legend>
                            <input :value="dayjs(product.availableUntil).format('YYYY-MM-DD')" type="date"
                                name="available_until" class="input w-full" />
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Disponible desde la hora</legend>
                            <input :value="product.availableFromTime" type="time" name="available_from_hour"
                                class="input w-full" />
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Disponible desde la hora</legend>
                            <input :value="product.availableUntilTime" type="time" name="available_until_hour"
                                class="input w-full" />
                        </fieldset>
                        <h1 class="font-bold text-gl lg:col-span-2">Dias disponible:</h1>
                        <div class="lg:col-span-2">
                            <div class="grid grid-cols-2 lg:grid-cols-4 xl:grid-cols-7 gap-3">
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-lg">
                                        <input name="monday" type="checkbox" class="checkbox"
                                            :checked="product.availableDays.includes(DayOfWeek.MONDAY)" />
                                        Lunes
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-lg">
                                        <input name="tuesday" type="checkbox" class="checkbox"
                                            :checked="product.availableDays.includes(DayOfWeek.TUESDAY)" />
                                        Martes
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-lg">
                                        <input name="wednesday" type="checkbox" class="checkbox"
                                            :checked="product.availableDays.includes(DayOfWeek.WEDNESDAY)" />
                                        Miercoles
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-lg">
                                        <input name="thursday" type="checkbox" class="checkbox"
                                            :checked="product.availableDays.includes(DayOfWeek.THURSDAY)" />
                                        Jueves
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-lg">
                                        <input name="friday" type="checkbox" class="checkbox"
                                            :checked="product.availableDays.includes(DayOfWeek.FRIDAY)" />
                                        Viernes
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-lg">
                                        <input name="saturday" type="checkbox" class="checkbox"
                                            :checked="product.availableDays.includes(DayOfWeek.SATURDAY)" />
                                        Sabado
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-lg">
                                        <input name="sunday" type="checkbox" class="checkbox"
                                            :checked="product.availableDays.includes(DayOfWeek.SUNDAY)" />
                                        Domingo
                                    </label>
                                </fieldset>
                            </div>
                        </div>
                        <fieldset class="fieldset py-2">
                            <legend class="fieldset-legend">Opciones</legend>
                            <label class="label">
                                <input type="checkbox" class="toggle toggle-success" name="isEnabled"
                                    :checked="product.isEnabled" />
                                Habilidado
                            </label>
                        </fieldset>
                        <div class="lg:col-span-2 lg:grid lg:justify-end mt-3">
                            <button class="w-full btn btn-primary lg:w-60">Actualizar</button>
                        </div>
                    </form>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>