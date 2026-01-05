<script setup>
import dayjs from 'dayjs';
import { getApiCashSessionsIdExpenses } from '~/services/cash-session-expense-resource/cash-session-expense-resource';

const { cashSessionId } = defineProps(['cashSessionId'])

const { params, setParam } = useParams(`adminGetApiCashSessionsIdExpenses-${cashSessionId}`, { page: 1 })

const { data: paginatedExpenses, status, refresh } = useAsyncData(`getApiCashSessionsIdExpenses-${cashSessionId}`,
    () => getApiCashSessionsIdExpenses(cashSessionId, params.value), { default: () => [] })

const prevPage = _ => setParam('page', params.value.page - 1)
const nextPage = _ => setParam('page', params.value.page + 1)

const categoryToText = (category) => {
    switch (category) {
        case 'SUPPLIES':
            return 'Suministros'
        case 'CLEANING':
            return 'Limpieza'
        case 'MAINTENANCE':
            return 'Mantenimiento'
        case 'WAGES':
            return 'Salarios'
        case 'OTHER':
            return 'Otros'
        default:
            return category
    }
}

watch(params, _ => refresh())
</script>

<template>
    <div class="overflow-x-auto rounded-box border border-base-content/5 bg-base-100">
        <table class="table table-zebra">
            <thead class="bg-base-200">
                <tr>
                    <th>#</th>
                    <th>Responsable</th>
                    <th>Monto</th>
                    <th>Categoria</th>
                    <th>Descripción</th>
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
                <tr v-if="status == 'success'" v-for="expense in paginatedExpenses.data" :key="expense.id">
                    <td>
                        <button class="btn btn-link" @click="copy(expense.id, 'ID copiado')">
                            {{ expense.id.slice(0, 8) }}...
                        </button>
                    </td>
                    <td>{{ expense.createdBy.name }}</td>
                    <td>${{ expense.amount }}</td>
                    <td>{{ categoryToText(expense.category) }}</td>
                    <td>
                        <div class="tooltip" :data-tip="expense.description">
                            {{ expense.description.slice(0, 9) }}...
                        </div>
                    </td>
                    <td>{{ dayjs(expense.createdAt).format('DD/MM/YYYY HH:mm') }}</td>
                    <td>
                        <NuxtLink :to="`/admin/cash-sessions?id=${expense.cashSession.id}`" target="_blank"
                            class="btn btn-outline btn-sm btn-primary tooltip" data-tip="Ver sesión de caja">
                            <Icon name="material-symbols:point-of-sale-rounded" class="text-xl" />
                        </NuxtLink>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="grid place-items-center mt-4">
        <div class="join" v-if="status == 'success'">
            <button class="join-item btn" :class="{ 'btn-disabled': !paginatedExpenses.meta.prevPage }"
                @click="prevPage">«</button>
            <button class="join-item btn btn-active cursor-auto">Page {{
                paginatedExpenses.meta.currentPage }}</button>
            <button class="join-item btn" :class="{ 'btn-disabled': !paginatedExpenses.meta.nextPage }"
                @click="nextPage">»</button>
        </div>
    </div>
</template>