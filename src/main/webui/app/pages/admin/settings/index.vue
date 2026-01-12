<script setup>
import { getApiBusinessprofile, putApiBusinessprofile } from '~/services/business-profile-resource/business-profile-resource';

definePageMeta({
    middleware: ['only-admin'],
})

const toast = useToast()

const { data: businessProfile, refresh, status } = useAsyncData(() => getApiBusinessprofile())

const onUpdateFormSubmit = (e) => putApiBusinessprofile({
    ...businessProfile.value,
    name: e.target.name.value,
    legalName: e.target.legalName.value,
    taxId: e.target.taxId.value,
    address: e.target.address.value,
    phone: e.target.phone.value,
    email: e.target.email.value,
    website: e.target.website.value,
})
    .then(_ => toast.success({ title: 'Perfil actualizado' }))
    .then(_ => refresh())
    .catch(_ => toast.error({ title: 'Error al actualizar el perfil' }))

</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Configuraciones">
            <div class="card bg-base-200 shadow-xl">
                <div class="card-body">
                    <p class="card-title text-xl">Perfil comercial</p>
                    <div class="divider m-0"></div>
                    <tr v-if="status == 'pending'">
                        <th colspan="8">
                            <div class="grid place-items-center">
                                <span class="loading loading-ring loading-xl"></span>
                            </div>
                        </th>
                    </tr>
                    <form v-if="status == 'success'" class="grid grid-cols-1 lg:grid-cols-2 gap-2"
                        @submit.prevent="onUpdateFormSubmit">
                        <div class="fieldset flex-1 p-0 w-full">
                            <legend class="fieldset-legend">Comercio</legend>
                            <input type="text" class="input w-full" name="name" :value="businessProfile.name" />
                        </div>
                        <div class="fieldset flex-1 p-0 w-full">
                            <legend class="fieldset-legend">Nombre legal</legend>
                            <input type="text" class="input w-full" name="legalName"
                                :value="businessProfile.legalName" />
                        </div>
                        <div class="fieldset flex-1 p-0 w-full">
                            <legend class="fieldset-legend">RFC</legend>
                            <input type="text" class="input w-full" name="taxId" :value="businessProfile.taxId" />
                        </div>
                        <div class="fieldset flex-1 p-0 w-full">
                            <legend class="fieldset-legend">Dirección</legend>
                            <input type="text" class="input w-full" name="address" :value="businessProfile.address" />
                        </div>
                        <div class="fieldset flex-1 p-0 w-full">
                            <legend class="fieldset-legend">Telefono</legend>
                            <input type="text" class="input w-full" name="phone" :value="businessProfile.phone" />
                        </div>
                        <div class="fieldset flex-1 p-0 w-full">
                            <legend class="fieldset-legend">Email</legend>
                            <input type="text" class="input w-full" name="email" :value="businessProfile.email" />
                        </div>
                        <div class="fieldset flex-1 p-0 w-full">
                            <legend class="fieldset-legend">Website</legend>
                            <input type="text" class="input w-full" name="website" :value="businessProfile.website" />
                        </div>
                        <div class="lg:col-span-2 lg:grid lg:justify-end mt-3">
                            <button class="w-full btn btn-primary lg:w-60">Actualizar perfil</button>
                        </div>
                    </form>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>