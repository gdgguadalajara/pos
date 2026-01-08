import { AccountRole } from "~/models";

export default defineNuxtRouteMiddleware((to, from) => {
    const toast = useToast()
    const user = JSON.parse(localStorage.getItem(Keys.USER) ?? '{}')
    if (user?.account?.role == AccountRole.WAITER) return
    toast.error({ title: 'Acceso no autorizado', message: 'Solo meseros pueden acceder a esta página', timeout: 2000 })
    if (user?.account?.role)
        return navigateTo(`/${user.account.role.toLowerCase()}`)
    return navigateTo('/login')
});