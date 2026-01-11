<script setup>
import dayjs from 'dayjs'
import { DayOfWeek } from '~/models'
import { getApiCategoriesUuid, putApiCategoriesUuid } from '~/services/category-resource/category-resource'
import { getApiProductionCenters } from '~/services/production-center-resource/production-center-resource'

definePageMeta({
    middleware: ['only-admin'],
})

const toast = useToast()
const route = useRoute()
const categoryId = route.params.id
const title = ref('Actualizar Categoria')

const { data: category, status: categoryStatus } = useLazyAsyncData(() =>
    getApiCategoriesUuid(categoryId))

const { data: paginatedProductionCenters } = useLazyAsyncData(() =>
    getApiProductionCenters({ size: 100 }), { default: () => [] })

const onSubmitUpdateCategory = (e) => {
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
        isEnabled: e.target.isEnabled.checked,
        availableFrom: dayjs(e.target.available_from.value).toISOString(),
        availableUntil: dayjs(e.target.available_until.value).toISOString(),
        availableFromTime: e.target.available_from_hour.value,
        availableUntilTime: e.target.available_until_hour.value,
        availableDays: availableDays,
        productionCenterId: e.target.production_center.value,
    }
    putApiCategoriesUuid(categoryId, payload)
        .then(_ => e.target.reset())
        .then(_ => toast.success({ title: 'Categoria actualizada' }))
        .then(_ => navigateTo("/admin/categories"))
        .catch(() => toast.error({ title: 'Error al actualizar la categoria' }))
}

watchEffect(() => {
    if (category.value != null)
        title.value = `Acualizar categoria: ${category.value.name}`
})
</script>

<template>
    <div>
        <NuxtLayout name="admin" :title="title">
            <div class="card bg-base-200 shadow-xl">
                <div class="card-body">
                    <div v-if="categoryStatus != 'success'" class="grid place-items-center">
                        <span class="loading loading-ring loading-xl"></span>
                    </div>
                    <form v-else @submit.prevent="onSubmitUpdateCategory" class="grid grid-cols-1 lg:grid-cols-2 gap-2">
                        <fieldset class="fieldset lg:col-span-2">
                            <legend class="fieldset-legend">Categoria</legend>
                            <input type="text" class="input w-full" name="name" :value="category.name" />
                        </fieldset>
                        <fieldset class="fieldset lg:col-span-2">
                            <legend class="fieldset-legend">Descripción</legend>
                            <textarea class="textarea w-full" name="description"
                                :value="category.description"></textarea>
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Disponible desde</legend>
                            <input :value="dayjs(category.availableFrom).format('YYYY-MM-DD')" type="date"
                                name="available_from" class="input w-full" />
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Disponible hasta</legend>
                            <input :value="dayjs(category.availableUntil).format('YYYY-MM-DD')" type="date"
                                name="available_until" class="input w-full" />
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Disponible desde la hora</legend>
                            <input :value="category.availableFromTime" type="time" name="available_from_hour"
                                class="input w-full" />
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Disponible desde la hora</legend>
                            <input :value="category.availableUntilTime" type="time" name="available_until_hour"
                                class="input w-full" />
                        </fieldset>
                        <h1 class="font-bold text-xs lg:col-span-2">Dias disponible:</h1>
                        <div class="lg:col-span-2">
                            <div class="grid grid-cols-2 lg:grid-cols-4 xl:grid-cols-7 gap-3">
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-xs">
                                        <input name="monday" type="checkbox" class="checkbox"
                                            :checked="category.availableDays.includes(DayOfWeek.MONDAY)" />
                                        Lunes
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-xs">
                                        <input name="tuesday" type="checkbox" class="checkbox"
                                            :checked="category.availableDays.includes(DayOfWeek.TUESDAY)" />
                                        Martes
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-xs">
                                        <input name="wednesday" type="checkbox" class="checkbox"
                                            :checked="category.availableDays.includes(DayOfWeek.WEDNESDAY)" />
                                        Miercoles
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-xs">
                                        <input name="thursday" type="checkbox" class="checkbox"
                                            :checked="category.availableDays.includes(DayOfWeek.THURSDAY)" />
                                        Jueves
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-xs">
                                        <input name="friday" type="checkbox" class="checkbox"
                                            :checked="category.availableDays.includes(DayOfWeek.FRIDAY)" />
                                        Viernes
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-xs">
                                        <input name="saturday" type="checkbox" class="checkbox"
                                            :checked="category.availableDays.includes(DayOfWeek.SATURDAY)" />
                                        Sabado
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-xs">
                                        <input name="sunday" type="checkbox" class="checkbox"
                                            :checked="category.availableDays.includes(DayOfWeek.SUNDAY)" />
                                        Domingo
                                    </label>
                                </fieldset>
                            </div>
                        </div>
                        <fieldset class="fieldset flex-1 p-0 w-full">
                            <legend class="fieldset-legend">Centro de producción</legend>
                            <select class="select w-full" name="production_center"
                                :value="category.productionCenter.id">
                                <option disabled="" value="" selected="true">Seleccione un centro de producción</option>
                                <option v-for="center in paginatedProductionCenters.data" :key="center.id"
                                    :value="center.id">
                                    {{ center.name }}
                                </option>
                            </select>
                        </fieldset>
                        <fieldset class="fieldset py-2">
                            <legend class="fieldset-legend">Opciones</legend>
                            <label class="label">
                                <input type="checkbox" class="toggle toggle-success" name="isEnabled"
                                    :checked="category.isEnabled" />
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