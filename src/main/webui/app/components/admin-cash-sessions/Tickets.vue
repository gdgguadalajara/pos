<script setup>
import { getApiCashSessionsIdTickets } from '~/services/cash-session-tickets-resource/cash-session-tickets-resource';

const { cashSessionId } = defineProps(['cashSessionId'])

definePageMeta({
    middleware: ['only-admin'],
})

const currentPage = ref(1)

const { data: paginatedTickets, status, refresh } = useAsyncData('getApiTickets', () => getApiCashSessionsIdTickets(cashSessionId, {
    page: currentPage.value
}), { default: () => [] })

const nextPage = () => {
    if (paginatedTickets.value.meta.nextPage) {
        currentPage.value += 1
        refresh()
    }
}
const prevPage = () => {
    if (paginatedTickets.value.meta.prevPage) {
        currentPage.value -= 1
        refresh()
    }
}
</script>

<template>
    <div class="overflow-x-auto rounded-box border border-base-content/5 bg-base-100">
        <table class="table table-zebra">
            <thead class="bg-base-200">
                <tr>
                    <th>#</th>
                    <th>Responable</th>
                    <th>Productos</th>
                    <th>Total</th>
                    <th>Estado</th>
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
                <tr v-if="status == 'success'" v-for="ticket in paginatedTickets.data" :key="ticket.id">
                    <td>
                        <button class="btn btn-link" @click="copyId(ticket.id)">
                            {{ ticket.id.slice(0, 8) }}...
                        </button>
                    </td>
                    <td>{{ ticket.owner.name }}</td>
                    <td>{{ ticket.items.length }}</td>
                    <td>${{ ticket.totalAmount }}</td>
                    <td>{{ ticket.status }}</td>
                    <td>
                        <div class="flex gap-1">
                            <div class="tooltip" data-tip="Ver detalles">
                                <NuxtLink :to="`/admin/tickets?id=${ticket.id}`" target="_blank"
                                    class="btn btn-outline btn-sm btn-primary">
                                    <Icon name="material-symbols:document-search-outline-rounded" class="text-2xl" />
                                </NuxtLink>
                            </div>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="grid place-items-center mt-4">
        <div class="join" v-if="status == 'success'">
            <button class="join-item btn" :class="{ 'btn-disabled': !paginatedTickets.meta.prevPage }"
                @click="prevPage">«</button>
            <button class="join-item btn btn-active cursor-auto">Page {{
                paginatedTickets.meta.currentPage }}</button>
            <button class="join-item btn" :class="{ 'btn-disabled': !paginatedTickets.meta.nextPage }"
                @click="nextPage">»</button>
        </div>
    </div>
</template>