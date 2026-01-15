<script setup>
const props = defineProps({
    title: {
        type: String,
        default: 'Point of Sale'
    }
})

const session = useSession()
const isLgScreen = useMediaQuery('(min-width: 1024px)')
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
                    <CashierCashSession v-if="isLgScreen" />
                    <div class="dropdown dropdown-end">
                        <div tabindex="0" role="button" class="btn btn-dash btn-ghost">
                            <Icon name="material-symbols:account-circle" class="text-2xl" />
                            {{ session.name }}
                        </div>
                        <ul tabindex="-1"
                            class="dropdown-content menu bg-base-100 rounded-box z-1 mt-1 w-40 px-3 py-1 shadow-sm">
                            <li>
                                <NuxtLink to="/cashier/profile">Profile</NuxtLink>
                            </li>
                            <li v-if="!isLgScreen"></li>
                            <li v-if="!isLgScreen">
                                <CashierCashSession />
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
            <div class="flex min-h-full flex-col items-start bg-base-200 is-drawer-close:w-14 is-drawer-open:w-52">

                <ul class="menu w-full grow">
                    <li class="menu-disabled">
                        <figure>
                            <img src="/favicon.svg" alt="favicon" class="">
                        </figure>
                    </li>
                    <li></li>
                    <li>
                        <NuxtLink to="/cashier/quick-sales"
                            class="is-drawer-close:tooltip is-drawer-close:tooltip-right" data-tip="Venta rapida">
                            <Icon name="material-symbols:point-of-sale-rounded" class="text-2xl" />
                            <span class="is-drawer-close:hidden">Venta rapida</span>
                        </NuxtLink>
                    </li>
                    <li>
                        <NuxtLink to="/cashier/tables" class="is-drawer-close:tooltip is-drawer-close:tooltip-right"
                            data-tip="Mesas">
                            <Icon name="material-symbols:table-bar-outline-rounded" class="text-2xl" />
                            <span class="is-drawer-close:hidden">Mesas</span>
                        </NuxtLink>
                    </li>
                    <li>
                        <NuxtLink to="/cashier/tickets" class="is-drawer-close:tooltip is-drawer-close:tooltip-right"
                            data-tip="Tickets">
                            <Icon name="material-symbols:receipt-long-outline-rounded" class="text-2xl" />
                            <span class="is-drawer-close:hidden">Tickets</span>
                        </NuxtLink>
                    </li>
                    <li>
                        <NuxtLink to="/cashier/expenses" class="is-drawer-close:tooltip is-drawer-close:tooltip-right"
                            data-tip="Gastos">
                            <Icon name="material-symbols:wallet" class="text-2xl" />
                            <span class="is-drawer-close:hidden">Gastos</span>
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