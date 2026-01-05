<script setup>
import dayjs from 'dayjs';
import { getApiTickets } from '~/services/ticket-resource/ticket-resource';

definePageMeta({
    middleware: ['only-admin'],
})

const route = useRoute()

const { params } = useParams('adminGetApiTicketsParams', { page: 1, id: route.query.id })

const { data: paginatedTickets, status, refresh } = useAsyncData('getApiTickets', () => getApiTickets(params.value), { default: () => [] })

const prevPage = _ => setParam('page', params.value.page - 1)
const nextPage = _ => setParam('page', params.value.page + 1)

watch(params, _ => refresh())
</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Tickets">
            <div class="card bg-base-200 shadow-xl">
                <div class="card-body">
                    <AdminTicketsFilters />
                    <div class="overflow-x-auto rounded-box border border-base-content/5 bg-base-100">
                        <table class="table table-zebra">
                            <thead class="bg-base-200">
                                <tr>
                                    <th>#</th>
                                    <th>Responable</th>
                                    <th>Productos</th>
                                    <th>Total</th>
                                    <th>Estado</th>
                                    <th>Creación</th>
                                    <th>Cierre</th>
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
                                        <button class="btn btn-link" @click="copy(ticket.id, 'ID copiado')">
                                            {{ ticket.id.slice(0, 8) }}...
                                        </button>
                                    </td>
                                    <td>{{ ticket.owner.name }}</td>
                                    <td>{{ ticket.items.length }}</td>
                                    <td>${{ ticket.totalAmount }}</td>
                                    <td>{{ ticket.status }}</td>
                                    <td>{{ dayjs(ticket.createdAt).format('DD/MM/YYYY HH:mm') }}</td>
                                    <td>{{ dayjs(ticket.closedAt).format('DD/MM/YYYY HH:mm') }}</td>
                                    <td>
                                        <div class="flex gap-1">
                                            <div class="tooltip" data-tip="Productos">
                                                <button class="btn btn-outline btn-sm btn-primary"
                                                    @click="openModal(`ticket-items-${ticket.id}`)">
                                                    <Icon name="material-symbols:package-2" class="text-2xl" />
                                                </button>
                                            </div>
                                            <dialog :id="`ticket-items-${ticket.id}`" class="modal">
                                                <div class="modal-box max-w-2xl max-h-4/5">
                                                    <AdminTicketsTicketProductsTable :items="ticket.items" />
                                                </div>
                                                <form method="dialog" class="modal-backdrop">
                                                    <button>close</button>
                                                </form>
                                            </dialog>
                                            <div class="tooltip" data-tip="Pagos">
                                                <button class="btn btn-outline btn-sm btn-info"
                                                    @click="openModal(`ticket-payments-${ticket.id}`)">
                                                    <Icon name="material-symbols:payments-outline-rounded"
                                                        class="text-2xl" />
                                                </button>
                                            </div>
                                            <dialog :id="`ticket-payments-${ticket.id}`" class="modal">
                                                <div class="modal-box max-w-2xl max-h-4/5">
                                                    <AdminTicketsTicketPaymentsTable :ticketId="ticket.id" />
                                                </div>
                                                <form method="dialog" class="modal-backdrop">
                                                    <button>close</button>
                                                </form>
                                            </dialog>
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
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>