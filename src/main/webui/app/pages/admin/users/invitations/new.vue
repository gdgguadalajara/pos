<script setup>
import { postApiInvitations } from '~/services/invitation-resource/invitation-resource'

definePageMeta({
    middleware: ['only-admin']
})

const toast = useToast()

const onSubmitCreateProduct = (e) => {
    const payload = {
        name: e.target.name.value,
        email: e.target.email.value,
        role: e.target.role.value
    }
    postApiInvitations(payload)
        .then(() => {
            e.target.reset()
            toast.success({ title: "Invitación creada" })
            navigateTo("/admin/users/invitations")
        })
        .catch(() => toast.error({ title: "Error al crear invitación" }))
}
</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Nueva invitación">
            <div class="card bg-base-200 shadow-xl">
                <div class="card-body">
                    <form @submit.prevent="onSubmitCreateProduct" class="grid grid-cols-1 gap-2">
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Nombre</legend>
                            <input type="text" class="input w-full" name="name" />
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Email</legend>
                            <input type="email" class="input w-full" name="email" />
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Rol</legend>
                            <select class="select w-full" name="role">
                                <option value="WAITER">Mesero</option>
                                <option value="CASHIER">Cajero</option>
                                <option value="ADMIN">Administrador</option>
                            </select>
                        </fieldset>
                        <div class="lg:grid lg:justify-end mt-3">
                            <button class="w-full btn btn-primary lg:w-60">Crear invitación</button>
                        </div>
                    </form>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>