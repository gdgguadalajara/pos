<script setup>
import { DayOfWeek } from '~/models'
import { postApiCategories } from '~/services/category-resource/category-resource'

definePageMeta({
    middleware: ['only-admin'],
})

const toast = useToast()

const onSubmitCreateCategory = (e) => {
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
        availableFrom: new Date(e.target.available_from.value),
        availableUntil: new Date(e.target.available_until.value),
        availableFromTime: e.target.available_from_hour.value,
        availableUntilTime: e.target.available_until_hour.value,
        availableDays: availableDays
    }
    postApiCategories(payload)
        .then(() => {
            e.target.reset()
            toast.success({ title: 'Categoria creada' })
            navigateTo("/admin/categories")
        })
        .catch(() => toast.error({ title: 'Error al crear la categoria' }))
}
</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Nueva Categoria">
            <div class="card bg-base-200 shadow-xl">
                <div class="card-body">
                    <form @submit.prevent="onSubmitCreateCategory" class="grid grid-cols-1 lg:grid-cols-2 gap-2">
                        <fieldset class="fieldset lg:col-span-2">
                            <legend class="fieldset-legend">Categoria</legend>
                            <input type="text" class="input w-full" name="name" />
                        </fieldset>
                        <fieldset class="fieldset lg:col-span-2">
                            <legend class="fieldset-legend">Descripción</legend>
                            <textarea class="textarea w-full" name="description"></textarea>
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Disponible desde</legend>
                            <input :value="new Date().toISOString().split('T')[0]" type="date" name="available_from"
                                class="input w-full" />
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Disponible hasta</legend>
                            <input
                                :value="new Date(new Date().getFullYear() + 1, new Date().getMonth(), new Date().getDate()).toISOString().split('T')[0]"
                                type="date" name="available_until" class="input w-full" />
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Disponible desde la hora</legend>
                            <input value="00:00" type="time" name="available_from_hour" class="input w-full" />
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Disponible desde la hora</legend>
                            <input value="23:59" type="time" name="available_until_hour" class="input w-full" />
                        </fieldset>
                        <h1 class="font-bold text-xs lg:col-span-2">Dias disponible:</h1>
                        <div class="lg:col-span-2">
                            <div class="grid grid-cols-2 lg:grid-cols-4 xl:grid-cols-7 gap-3">
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-xs">
                                        <input name="monday" type="checkbox" class="checkbox" checked />
                                        Lunes
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-xs">
                                        <input name="tuesday" type="checkbox" class="checkbox" checked />
                                        Martes
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-xs">
                                        <input name="wednesday" type="checkbox" class="checkbox" checked />
                                        Miercoles
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-xs">
                                        <input name="thursday" type="checkbox" class="checkbox" checked />
                                        Jueves
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-xs">
                                        <input name="friday" type="checkbox" class="checkbox" checked />
                                        Viernes
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-xs">
                                        <input name="saturday" type="checkbox" class="checkbox" checked />
                                        Sabado
                                    </label>
                                </fieldset>
                                <fieldset class="fieldset bg-base-200 rounded-box">
                                    <label class="label text-xs">
                                        <input name="sunday" type="checkbox" class="checkbox" checked />
                                        Domingo
                                    </label>
                                </fieldset>
                            </div>
                        </div>
                        <fieldset class="fieldset py-2">
                            <legend class="fieldset-legend">Opciones</legend>
                            <label class="label">
                                <input type="checkbox" checked class="toggle toggle-success" name="isEnabled" />
                                Habilidado
                            </label>
                        </fieldset>
                        <div class="lg:col-span-2 lg:grid lg:justify-end mt-3">
                            <button class="w-full btn btn-primary lg:w-60">Crear nueva categoria</button>
                        </div>
                    </form>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>