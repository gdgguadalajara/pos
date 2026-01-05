<script setup>
import dayjs from 'dayjs'
import { DayOfWeek } from '~/models'
import { postApiProducts } from '~/services/product-resource/product-resource'

definePageMeta({
    middleware: ['only-admin'],
})

const toast = useToast()

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
        description: '', // TODO
        price: +e.target.price.value,
        isEnabled: true, // TODO
        // TODO: add categories to link
        availableFrom: dayjs(e.target.available_from.value).toISOString(),
        availableUntil: dayjs(e.target.available_until.value).toISOString(),
        availableFromTime: e.target.available_from_hour.value,
        availableUntilTime: e.target.available_until_hour.value,
        availableDays: availableDays
    }
    postApiProducts(payload)
        .then(_ => e.target.reset())
        .then(_ => toast.success({ title: "Producto creado" }))
        .then(_ => navigateTo("/admin/products"))
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
                        <div class="lg:col-span-2 lg:grid lg:justify-end mt-3">
                            <button class="w-full btn btn-primary lg:w-60">Crear nuevo producto</button>
                        </div>
                    </form>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>