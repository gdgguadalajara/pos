<script setup>
import { postApiProductionCenters } from '~/services/production-center-resource/production-center-resource';

definePageMeta({
    middleware: ['only-admin'],
})

const toast = useToast()

const onSubmitCreateCenter = (e) => {
    postApiProductionCenters({
        name: e.target.name.value,
        description: e.target.description.value,
        isActive: e.target.isEnabled.checked,
    })
        .then(_ => e.target.reset())
        .then(_ => toast.success({ title: 'Centro de producción creado' }))
        .then(_ => navigateTo("/admin/production_centers"))
        .catch(_ => toast.error({ title: 'Error al crear la centro de producción' }))
}
</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Nuevo centro de producción">
            <div class="card bg-base-200 shadow-xl">
                <div class="card-body">
                    <form @submit.prevent="onSubmitCreateCenter" class="grid grid-cols-1 lg:grid-cols-2 gap-2">
                        <fieldset class="fieldset lg:col-span-2">
                            <legend class="fieldset-legend">Centro de producción</legend>
                            <input type="text" class="input w-full" name="name" />
                        </fieldset>
                        <fieldset class="fieldset lg:col-span-2">
                            <legend class="fieldset-legend">Descripción</legend>
                            <textarea class="textarea w-full" name="description"></textarea>
                        </fieldset>
                        <fieldset class="fieldset py-2">
                            <legend class="fieldset-legend">Opciones</legend>
                            <label class="label">
                                <input type="checkbox" checked class="toggle toggle-success" name="isEnabled" />
                                Habilidado
                            </label>
                        </fieldset>
                        <div class="lg:col-span-2 lg:grid lg:justify-end mt-3">
                            <button class="w-full btn btn-primary lg:w-60">Crear</button>
                        </div>
                    </form>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>