<script setup>
import { getApiUsers } from '~/services/user-resource/user-resource';

definePageMeta({
    middleware: ['only-admin'],
})

const { params, setParam } = useParams('adminGetApiUsersParams', { page: 1, sort: 'name' })

const { data: paginatedUsers, status, refresh } = useAsyncData('getApiUsers', () => getApiUsers(params.value))

const nextPage = () => setParam('page', params.value.page + 1)
const prevPage = () => setParam('page', params.value.page - 1)

const accountStatusToText = (status) => {
    switch (status) {
        case 'PENDING_SETUP': return 'No configurado'
        case 'ACTIVE': return 'Activo'
        case 'LOCKED': return 'Bloqueado'
        case 'DISABLED': return 'Deshabilitado'
        default: return status
    }
}

const accountRoleToText = (status) => {
    switch (status) {
        case 'ADMIN': return 'Administrador'
        case 'CASHIER': return 'Cajero'
        case 'WAITER': return 'Mesero'
        default: return status
    }
}

watch(params, () => refresh())
</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Usuarios">
            <div class="card bg-base-200 shadow-xl">
                <div class="card-body">
                    <div class="mb-3">
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
                    <AdminUsersFilters />
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
                                        <button class="btn btn-link" @click="copy(user.id, 'ID copiado')">
                                            {{ user.id.slice(0, 8) }}...
                                        </button>
                                    </td>
                                    <td>{{ user.name }}</td>
                                    <td>{{ user.email }}</td>
                                    <td>{{ user.account.username }}</td>
                                    <td>{{ accountStatusToText(user.account.status) }}</td>
                                    <td>{{ accountRoleToText(user.account.role) }}</td>
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