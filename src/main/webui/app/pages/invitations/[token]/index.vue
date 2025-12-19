<script setup>
import { AccountStatus } from '~/models';
import { postApiAccountsActivate } from '~/services/account-resource/account-resource';
import { getApiInvitationsToken } from '~/services/invitation-resource/invitation-resource';

const toast = useToast()
const route = useRoute()

const token = route.params.token

const { data: invitation, status } = useAsyncData(token, () => getApiInvitationsToken(token))

watch(invitation, () => {
    if (invitation.value?.user?.account?.status != AccountStatus.PENDING_SETUP) {
        toast.error({ title: 'Error', message: 'No esta autorizado para acceder a esta página' })
        return navigateTo('/login')
    }
})

const onSubmitSetup = (e) => {
    const password = e.target.elements.namedItem('password').value
    const confirmPassword = e.target.elements.namedItem('confirmPassword').value
    if (password != confirmPassword) {
        return toast.error({ title: 'Error', message: 'Las contraseñas no coinciden' })
    }
    postApiAccountsActivate({ invitationToken: token, password: password })
        .then(() => {
            toast.success({ title: 'Éxito', message: 'Tu cuenta ha sido configurada' })
            navigateTo('/login')
        })
        .catch(() => {
            toast.error({ title: 'Error', message: 'Ocurrió un error al configurar tu cuenta' })
        })
}
</script>

<template>
    <div v-if="status == 'pending'" class="h-screen w-screen grid place-items-center">
        <span class="loading loading-ring loading-xl"></span>
    </div>
    <div v-else class="grid place-items-center h-screen">
        <div class="card bg-base-200 shadow-xl min-w-xl">
            <div class="card-body">
                <h3 class="card-title">Configura tu cuenta</h3>
                <p>Bienvenido {{ invitation.user?.name }}</p>
                <form id="account-setup-form" @submit.prevent="onSubmitSetup">
                    <fieldset class="fieldset">
                        <legend class="fieldset-legend">Contraseña</legend>
                        <input autocomplete="new-password" type="password" class="input w-full" name="password" />
                    </fieldset>
                    <fieldset class="fieldset">
                        <legend class="fieldset-legend">Confirma tu contraseña</legend>
                        <input autocomplete="new-password" type="password" class="input w-full"
                            name="confirmPassword" />
                    </fieldset>
                </form>
                <div class="card-actions justify-end">
                    <button class="btn btn-primary w-full" form="account-setup-form" type="submit">
                        Configurar contraseña
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>