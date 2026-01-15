<script setup>
import dayjs from 'dayjs';
import { getApiCashSessions } from '~/services/cash-session-resource/cash-session-resource';

definePageMeta({
    middleware: ['only-admin'],
})

const route = useRoute()

const { params, setParam } = useParams('adminGetApiCashSessionsParams', { page: 1, id: route.query.id })

const { data: PaginatedCashSessions, status, refresh } = useAsyncData('getApiCashSessions', () => getApiCashSessions(params.value))

const prevPage = _ => setParam('page', params.value.page - 1)
const nextPage = _ => setParam('page', params.value.page + 1)

watch(params, _ => refresh())
</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Sesiones de caja">
            <div class="card bg-base-200 shadow-xl">
                <div class="card-body">
                    <AdminCashSessionsFilters />
                    <div class="overflow-x-auto rounded-box border border-base-content/5 bg-base-100">
                        <table class="table table-zebra">
                            <thead class="bg-base-200">
                                <tr>
                                    <th>#</th>
                                    <th>Abierto por</th>
                                    <th>Cerrado por</th>
                                    <th>Inicio</th>
                                    <th>Cerro</th>
                                    <th>Balance inicial</th>
                                    <th>Ventas totales</th>
                                    <th>Gastos totales</th>
                                    <th>Balance reportado</th>
                                    <th>Diferencia</th>
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
                                <tr v-if="status == 'success'" v-for="cashSession in PaginatedCashSessions.data"
                                    :key="cashSession.id" :class="{ 'bg-red-500/10': cashSession.difference < 0 }">
                                    <td>
                                        <button class="btn btn-link" @click="copy(cashSession.id, 'ID copiado')">
                                            {{ cashSession.id.slice(0, 8) }}...
                                        </button>
                                    </td>
                                    <td>{{ cashSession.openedBy.name }}</td>
                                    <td>{{ cashSession.closedBy?.name }}</td>
                                    <td>{{ dayjs(cashSession.openingDate).format('DD/MM/YYYY HH:mm') }}</td>
                                    <td>{{ cashSession.closingDate
                                        ? dayjs(cashSession.closingDate).format('DD/MM/YYYY HH: mm')
                                        : '' }}</td>
                                    <td>${{ cashSession.initialBalance }}</td>
                                    <td>${{ cashSession.totalSales }}</td>
                                    <td>${{ cashSession.totalExpenses }}</td>
                                    <td>${{ cashSession.reportedBalance }}</td>
                                    <td>${{ cashSession.difference }}</td>
                                    <td>{{ cashSession.status == 'OPEN' ? 'Abierto' : 'Cerrado' }}</td>
                                    <td>
                                        <div class="flex gap-1">
                                            <div class="tooltip" data-tip="Pagos">
                                                <button class="btn btn-outline btn-sm btn-primary"
                                                    @click="openModal(`tickets-cashsession-${cashSession.id}`)">
                                                    <Icon name="material-symbols:payments-outline-rounded"
                                                        class="text-2xl" />
                                                </button>
                                            </div>
                                            <dialog :id="`tickets-cashsession-${cashSession.id}`" class="modal">
                                                <div class="modal-box max-w-4xl max-h-4/5">
                                                    <AdminCashSessionsPayments :cashSessionId="cashSession.id" />
                                                </div>
                                                <form method="dialog" class="modal-backdrop">
                                                    <button>close</button>
                                                </form>
                                            </dialog>
                                            <div class="tooltip" data-tip="Notas">
                                                <button class="btn btn-outline btn-sm btn-info"
                                                    @click="openModal(`tickets-cashsession-${cashSession.id}-notes`)">
                                                    <Icon name="material-symbols:notes-rounded" class="text-2xl" />
                                                </button>
                                            </div>
                                            <dialog :id="`tickets-cashsession-${cashSession.id}-notes`" class="modal">
                                                <div class="modal-box max-w-4xl max-h-4/5">
                                                    <h2 class="text-2xl">Notas</h2>
                                                    <p>{{ cashSession.note }}</p>
                                                </div>
                                                <form method="dialog" class="modal-backdrop">
                                                    <button>close</button>
                                                </form>
                                            </dialog>
                                            <div class="tooltip" data-tip="Gastos">
                                                <button class="btn btn-outline btn-sm btn-secondary"
                                                    @click="openModal(`tickets-cashsession-${cashSession.id}-expenses`)">
                                                    <Icon name="material-symbols:point-of-sale-rounded"
                                                        class="text-2xl" />
                                                </button>
                                            </div>
                                            <dialog :id="`tickets-cashsession-${cashSession.id}-expenses`"
                                                class="modal">
                                                <div class="modal-box max-w-4xl max-h-4/5">
                                                    <AdminCashSessionsExpenses :cashSessionId="cashSession.id" />
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
                            <button class="join-item btn"
                                :class="{ 'btn-disabled': !PaginatedCashSessions.meta.prevPage }"
                                @click="prevPage">«</button>
                            <button class="join-item btn btn-active cursor-auto">Page {{
                                PaginatedCashSessions.meta.currentPage }}</button>
                            <button class="join-item btn"
                                :class="{ 'btn-disabled': !PaginatedCashSessions.meta.nextPage }"
                                @click="nextPage">»</button>
                        </div>
                    </div>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>