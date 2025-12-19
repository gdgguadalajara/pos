<script setup>
import { getApiUsers } from '~/services/user-resource/user-resource';

definePageMeta({
    middleware: ['only-admin'],
})

const toast = useToast()

const currentPage = ref(1)

const { data: paginatedUsers, status, refresh } = useAsyncData('admin_users', () => getApiUsers({
    page: currentPage.value
}))

const nextPage = () => {
    if (paginatedUsers.value.meta.nextPage) {
        currentPage.value += 1
        refresh()
    }
}
const prevPage = () => {
    if (paginatedUsers.value.meta.prevPage) {
        currentPage.value -= 1
        refresh()
    }
}

const copyId = (text) =>
    navigator.clipboard.writeText(text)
        .then(_ => toast.info({ title: "ID Copiado" }))

const accountStatusToText = (status) => {
    switch (status) {
        case 'PENDING_SETUP':
            return 'No configurado'
        case 'ACTIVE':
            return 'Activo'
        case 'LOCKED':
            return 'Bloqueado'
        case 'DISABLED':
            return 'Deshabilitado'
        default:
            return status
    }
}

</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Usuarios">
            <div class="card bg-base-200 shadow-xl">
                <div class="card-body">
                    <div class="flex justify-end mb-3">
                        <NuxtLink to="/admin/users/invitations/new" class="btn btn-primary">
                            Nuevo Usuario
                        </NuxtLink>
                    </div>
                    <div role="tablist" class="bg-base-100 tabs tabs-box">
                        <NuxtLink to="/admin/users" role="tab" class="tab tab-active">
                            Usuarios
                        </NuxtLink>
                        <NuxtLink to="/admin/users/invitations" role="tab" class="tab">
                            Invitaciones
                        </NuxtLink>
                    </div>
                    <div class="overflow-x-auto rounded-box border border-base-content/5 bg-base-100">
                        <table class="table table-zebra">
                            <thead class="bg-base-200">
                                <tr>
                                    <th>#</th>
                                    <th>Nombre</th>
                                    <th>Email</th>
                                    <th>Usuario</th>
                                    <th>Estatus</th>
                                    <th>Rol</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-if="status == 'pending'">
                                    <th colspan="8">
                                        <div class="grid place-items-center">
                                            <span class="loading loading-ring loading-xl"></span>
                                        </div>
                                    </th>
                                </tr>
                                <tr v-if="status == 'success'" v-for="user in paginatedUsers.data" :key="user.id">
                                    <td>
                                        <button class="btn btn-link" @click="copyId(user.id)">
                                            {{ user.id.slice(0, 8) }}...
                                        </button>
                                    </td>
                                    <td>{{ user.name }}</td>
                                    <td>{{ user.email }}</td>
                                    <td>{{ user.account.username }}</td>
                                    <td>{{ accountStatusToText(user.account.status) }}</td>
                                    <td>{{ user.account.role }}</td>
                                    <td>
                                        <div class="flex gap-1">
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="grid place-items-center mt-4">
                        <div class="join" v-if="status == 'success'">
                            <button class="join-item btn" :class="{ 'btn-disabled': !paginatedUsers.meta.prevPage }"
                                @click="prevPage">«</button>
                            <button class="join-item btn btn-active cursor-auto">Page {{
                                paginatedUsers.meta.currentPage }}</button>
                            <button class="join-item btn" :class="{ 'btn-disabled': !paginatedUsers.meta.nextPage }"
                                @click="nextPage">»</button>
                        </div>
                    </div>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>