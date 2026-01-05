<script setup>
import dayjs from 'dayjs';
import { getApiPayments } from '~/services/payment-resource/payment-resource';

const { cashSessionId } = defineProps(['cashSessionId'])

const { params, setParam } = useParams(`adminGetApiCashSessionPayments-${cashSessionId}`, { page: 1, cashSessionId })

const { data: paginatedPayments, status, refresh } = useAsyncData(`getApiPayments-${cashSessionId}`,
    () => getApiPayments(params.value), { default: () => [] })

const prevPage = _ => setParam('page', params.value.page - 1)
const nextPage = _ => setParam('page', params.value.page + 1)

watch(params, _ => refresh())
</script>

<template>
    <div class="overflow-x-auto rounded-box border border-base-content/5 bg-base-100">
        <table class="table table-zebra">
            <thead class="bg-base-200">
                <tr>
                    <th>#</th>
                    <th>Metodo</th>
                    <th>Referencia</th>
                    <th>Total</th>
                    <th>Fecha</th>
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
                <tr v-if="status == 'success'" v-for="payment in paginatedPayments.data" :key="payment.id">
                    <td>
                        <button class="btn btn-link" @click="copy(payment.id, 'ID copiado')">
                            {{ payment.id.slice(0, 8) }}...
                        </button>
                    </td>
                    <td>{{ payment.method }}</td>
                    <td>{{ payment.reference }}</td>
                    <td>${{ payment.amount - payment.changeGiven }}</td>
                    <td>{{ dayjs(payment.createdAt).format('DD/MM/YYYY HH:mm') }}</td>
                    <td>
                        <div class="flex gap-1">
                            <div class="tooltip" data-tip="Ver ticket">
                                <NuxtLink :to="`/admin/tickets?id=${payment.ticket.id}`" target="_blank"
                                    class="btn btn-outline btn-sm btn-primary">
                                    <Icon name="material-symbols:receipt-long-outline-rounded" class="text-2xl" />
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
            <button class="join-item btn" :class="{ 'btn-disabled': !paginatedPayments.meta.prevPage }"
                @click="prevPage">«</button>
            <button class="join-item btn btn-active cursor-auto">Page {{
                paginatedPayments.meta.currentPage }}</button>
            <button class="join-item btn" :class="{ 'btn-disabled': !paginatedPayments.meta.nextPage }"
                @click="nextPage">»</button>
        </div>
    </div>
</template>