<script setup>
import dayjs from 'dayjs';
import { getApiInvitations } from '~/services/invitation-resource/invitation-resource';

definePageMeta({
    middleware: ['only-admin'],
})

const toast = useToast()
const url = useRequestURL()

const currentPage = ref(1)

const { data: paginatedInvitations, status, refresh } = useAsyncData('admin_invitations', () => getApiInvitations({
    page: currentPage.value
}))

const nextPage = () => {
    if (paginatedInvitations.value.meta.nextPage) {
        currentPage.value += 1
        refresh()
    }
}
const prevPage = () => {
    if (paginatedInvitations.value.meta.prevPage) {
        currentPage.value -= 1
        refresh()
    }
}

const copyId = (text) =>
    navigator.clipboard.writeText(text)
        .then(_ => toast.info({ title: "ID Copiado" }))

const copyInvitationLink = (token) =>
    navigator.clipboard.writeText(`${url.host}/invitations/${token}`)
        .then(_ => toast.info({ title: "Link copiado" }))

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
                    <div class="mb-3">
                        <NuxtLink to="/admin/users/invitations/new" class="btn btn-primary">
                            Nuevo Usuario
                        </NuxtLink>
                    </div>
                    <div role="tablist" class="bg-base-100 tabs tabs-box">
                        <NuxtLink to="/admin/users" role="tab" class="tab">
                            Usuarios
                        </NuxtLink>
                        <NuxtLink to="/admin/users/invitations" role="tab" class="tab tab-active">
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
                                    <th>Estatus</th>
                                    <th>Rol</th>
                                    <th>Expiración</th>
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
                                <tr v-if="status == 'success'" v-for="invitation in paginatedInvitations.data"
                                    :key="invitation.id">
                                    <td>
                                        <button class="btn btn-link" @click="copyId(invitation.id)">
                                            {{ invitation.id.slice(0, 8) }}...
                                        </button>
                                    </td>
                                    <td>{{ invitation.user.name }}</td>
                                    <td>{{ invitation.user.email }}</td>
                                    <td>{{ accountStatusToText(invitation.user.account.status) }}</td>
                                    <td>{{ invitation.user.account.role }}</td>
                                    <td>{{ dayjs(invitation.expiresAt).diff(dayjs(), 'day') }} dias</td>
                                    <td>
                                        <div class="flex gap-1">
                                            <div class="tooltip" data-tip="Link de invitación">
                                                <button class="btn btn-outline btn-sm btn-info"
                                                    @click="copyInvitationLink(invitation.token)">
                                                    <Icon name="material-symbols:link" class="text-xl" />
                                                </button>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="grid place-items-center mt-4">
                        <div class="join" v-if="status == 'success'">
                            <button class="join-item btn"
                                :class="{ 'btn-disabled': !paginatedInvitations.meta.prevPage }"
                                @click="prevPage">«</button>
                            <button class="join-item btn btn-active cursor-auto">Page {{
                                paginatedInvitations.meta.currentPage }}</button>
                            <button class="join-item btn"
                                :class="{ 'btn-disabled': !paginatedInvitations.meta.nextPage }"
                                @click="nextPage">»</button>
                        </div>
                    </div>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>