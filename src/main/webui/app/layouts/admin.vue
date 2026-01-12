<script setup>
const props = defineProps({
    title: {
        type: String,
        default: 'Point of Sale'
    }
})

const user = JSON.parse(localStorage.getItem(Keys.USER))
</script>

<template>
    <div class="drawer lg:drawer-open h-screen w-screen">
        <input id="admin-drawer" type="checkbox" class="drawer-toggle" />
        <div class="drawer-content flex flex-col overflow-y-auto">
            <nav class="flex-none navbar w-full bg-base-300 sticky top-0 z-10">
                <div class="flex items-center flex-1">
                    <label for="admin-drawer" aria-label="open sidebar" class="btn btn-square btn-ghost">
                        <Icon name="material-symbols:left-panel-open-outline" class="text-2xl" />
                    </label>
                    <div class="px-4">{{ props.title }}</div>
                </div>
                <div class="flex items-center gap-1 flex-none mr-3">
                    <div class="dropdown">
                        <div tabindex="0" role="button" class="btn btn-dash btn-ghost">
                            <Icon name="material-symbols:account-circle" class="text-2xl" />
                            {{ user.name }}
                        </div>
                        <ul tabindex="-1"
                            class="dropdown-content menu bg-base-100 rounded-box z-1 mt-1 w-40 px-3 py-1 shadow-sm">
                            <li>
                                <NuxtLink to="/admin/profile">Profile</NuxtLink>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <div class="flex-1 m-5">
                <slot></slot>
            </div>
        </div>

        <div class="drawer-side is-drawer-close:overflow-visible">
            <label for="admin-drawer" aria-label="close sidebar" class="drawer-overlay"></label>
            <div class="flex min-h-full flex-col items-start bg-base-200 is-drawer-close:w-14 is-drawer-open:w-60">

                <ul class="menu w-full grow">
                    <li class="menu-disabled">
                        <figure>
                            <img src="/favicon.svg" alt="favicon" class="">
                        </figure>
                    </li>
                    <li></li>
                    <li>
                        <NuxtLink to="/admin/dashboard" class="is-drawer-close:tooltip is-drawer-close:tooltip-right"
                            data-tip="Dashboard">
                            <Icon name="material-symbols:dashboard-outline-rounded" class="text-2xl" />
                            <span class="is-drawer-close:hidden">Dashboard</span>
                        </NuxtLink>
                    </li>
                    <li>
                        <NuxtLink to="/admin/users" class="is-drawer-close:tooltip is-drawer-close:tooltip-right"
                            data-tip="Usuarios">
                            <Icon name="material-symbols:supervisor-account" class="text-2xl" />
                            <span class="is-drawer-close:hidden">Usuarios</span>
                        </NuxtLink>
                    </li>
                    <li>
                        <NuxtLink to="/admin/production-centers"
                            class="is-drawer-close:tooltip is-drawer-close:tooltip-right"
                            data-tip="Centros de produccion">
                            <Icon name="material-symbols:factory-outline" class="text-2xl" />
                            <span class="is-drawer-close:hidden">Centros de producción</span>
                        </NuxtLink>
                    </li>
                    <li>
                        <NuxtLink to="/admin/categories" class="is-drawer-close:tooltip is-drawer-close:tooltip-right"
                            data-tip="Categorias">
                            <Icon name="material-symbols:category-rounded" class="text-2xl" />
                            <span class="is-drawer-close:hidden">Categorias</span>
                        </NuxtLink>
                    </li>
                    <li>
                        <NuxtLink to="/admin/products" class="is-drawer-close:tooltip is-drawer-close:tooltip-right"
                            data-tip="Productos">
                            <Icon name="material-symbols:fastfood" class="text-2xl" />
                            <span class="is-drawer-close:hidden">Productos</span>
                        </NuxtLink>
                    </li>
                    <li>
                        <NuxtLink to="/admin/floorplan" class="is-drawer-close:tooltip is-drawer-close:tooltip-right"
                            data-tip="Mapa de mesas">
                            <Icon name="material-symbols:map-outline-rounded" class="text-2xl" />
                            <span class="is-drawer-close:hidden">Mapa de mesas</span>
                        </NuxtLink>
                    </li>
                    <li>
                        <NuxtLink to="/admin/cash-sessions"
                            class="is-drawer-close:tooltip is-drawer-close:tooltip-right" data-tip="Sesiones de caja">
                            <Icon name="material-symbols:point-of-sale-rounded" class="text-2xl" />
                            <span class="is-drawer-close:hidden">Sesiones de caja</span>
                        </NuxtLink>
                    </li>
                    <li>
                        <NuxtLink to="/admin/tickets" class="is-drawer-close:tooltip is-drawer-close:tooltip-right"
                            data-tip="Tickets">
                            <Icon name="material-symbols:receipt-long-outline-rounded" class="text-2xl" />
                            <span class="is-drawer-close:hidden">Tickets</span>
                        </NuxtLink>
                    </li>
                    <li>
                        <NuxtLink to="/admin/expenses" class="is-drawer-close:tooltip is-drawer-close:tooltip-right"
                            data-tip="Gastos">
                            <Icon name="material-symbols:wallet" class="text-2xl" />
                            <span class="is-drawer-close:hidden">Gastos</span>
                        </NuxtLink>
                    </li>
                    <li>
                        <NuxtLink to="/admin/settings" class="is-drawer-close:tooltip is-drawer-close:tooltip-right"
                            data-tip="Configuraciones">
                            <Icon name="material-symbols:settings" class="text-2xl" />
                            <span class="is-drawer-close:hidden">Configuraciones</span>
                        </NuxtLink>
                    </li>
                    <li></li>
                    <li>
                        <NuxtLink to="/logout" class="is-drawer-close:tooltip is-drawer-close:tooltip-right"
                            data-tip="Cerrar Sesión">
                            <Icon name="material-symbols:exit-to-app-rounded" class="text-2xl" />
                            <span class="is-drawer-close:hidden">Cerrar Sesión</span>
                        </NuxtLink>
                    </li>

                </ul>
            </div>
        </div>
    </div>
</template>