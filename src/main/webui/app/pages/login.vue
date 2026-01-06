<script setup>
import { postApiAuth } from '@/services/auth-resource/auth-resource'
import { AccountRole } from '@/models/accountRole'
import { AccountStatus } from '~/models'

const toast = useToast()

const onLoginSubmit = (e) => {
    const email = e.target.elements.namedItem('email').value;
    const password = e.target.elements.namedItem('password').value;

    postApiAuth({ email, password }).then(res => {
        const token = res.token
        const user = res.user
        if (user.account.status != AccountStatus.ACTIVE) {
            return toast.error("Cuenta no activa, contacte al administrador.")
        }
        localStorage.setItem(Keys.TOKEN, token)
        localStorage.setItem(Keys.USER, JSON.stringify(user))
        useState(Keys.USER).value = user
        toast.success({
            title: 'Sesión iniciada',
            message: 'Bienvenido ' + user.name
        })
        switch (user.account.role) {
            case AccountRole.ADMIN:
                return navigateTo('/admin')
            case AccountRole.CASHIER:
                return navigateTo('/cashier')
        }
    }).catch((e) => toast.error({ title: 'Error', message: e.message }))
}
</script>

<template>
    <div class="grid place-items-center h-screen">
        <div class="card lg:card-side bg-base-200 shadow-sm max-w-lg">
            <figure>
                <img src="/favicon.svg" alt="favicon" class="h-42 mt-6 lg:mt-0 lg:h-52 lg:ml-5" />
            </figure>
            <div class="card-body">
                <h2 class="card-title">Punto de Venta</h2>
                <form id="login-form" @submit.prevent="onLoginSubmit">
                    <fieldset class="fieldset">
                        <legend class="fieldset-legend">Usuario o Correo</legend>
                        <input autocomplete="email" type="text" class="input" name="email" />
                    </fieldset>
                    <fieldset class="fieldset">
                        <legend class="fieldset-legend">Contraseña</legend>
                        <input autocomplete="current-password" type="password" name="password" class="input" />
                    </fieldset>
                </form>
                <div class="card-actions justify-end">
                    <button class="btn btn-primary w-full" form="login-form" type="submit">
                        Iniciar Sesión
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>