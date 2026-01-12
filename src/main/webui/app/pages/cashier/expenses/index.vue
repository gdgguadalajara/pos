<script setup>
import dayjs from 'dayjs';
import { getApiExpenses } from '~/services/expense-resource/expense-resource';

definePageMeta({
    middleware: ['only-cashier'],
})

const { params, setParam } = useParams('cashierGetApiExpensesParams', { page: 1 })

const { data: paginatedExpenses, status, refresh } = useAsyncData('getApiExpenses', () => getApiExpenses(params.value), { default: () => [] })

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
    <div>
        <NuxtLayout name="cashier" title="Gastos">
            <div class="card bg-base-200 shadow-xl">
                <div class="card-body">
                    <div class="mb-3">
                        <NuxtLink to="/cashier/expenses/new" class="btn btn-primary flex-none">
                            Nuevo Gasto
                        </NuxtLink>
                    </div>
                    <div class="overflow-x-auto rounded-box border border-base-content/5 bg-base-100">
                        <table class="table table-zebra">
                            <thead class="bg-base-200">
                                <tr>
                                    <th>#</th>
                                    <th>Responsable</th>
                                    <th>Monto</th>
                                    <th>Categoria</th>
                                    <th>Descripción</th>
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
                                <tr v-if="status == 'success'" v-for="expense in paginatedExpenses.data"
                                    :key="expense.id">
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
                                    <td>
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
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>