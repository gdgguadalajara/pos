<script setup>
const props = defineProps({
    title: {
        type: String,
        default: 'Point of Sale'
    }
})

const session = useSession()
</script>

<template>
    <nav class="navbar bg-base-300 sticky top-0 z-10">
        <div class="flex-1">
            <a class="btn btn-ghost text-xl">{{ props.title }}</a>
        </div>
        <div class="flex items-center gap-1 flex-none mr-3">
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
                    <li></li>
                    <li>
                        <NuxtLink to="/logout">Cerrar sesión</NuxtLink>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <slot></slot>
</template>