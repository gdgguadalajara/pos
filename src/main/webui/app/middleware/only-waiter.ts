import { AccountRole } from "~/models";

export default defineNuxtRouteMiddleware((to, from) => {
    const toast = useToast()
    const user = JSON.parse(localStorage.getItem(Keys.USER) ?? '{}')
    if (user?.account?.role == AccountRole.WAITER) return
    toast.error({ title: 'Acceso no autorizado', message: 'Solo meseros pueden acceder a esta página', timeout: 2000 })
    switch (user.account.role) {
        case AccountRole.ADMIN:
            return navigateTo('/admin')
        case AccountRole.CASHIER:
            return navigateTo('/cashier')
    }
});