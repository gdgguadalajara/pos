<script setup>
import dayjs from 'dayjs'
import {
    getApiDashboardAverageOrderValue,
    getApiDashboardAverageOrderValueSingle,
    getApiDashboardProductPerformance
} from '~/services/dashboard-resource/dashboard-resource'

definePageMeta({
    middleware: ['only-admin'],
})

const getWeekRange = (date = new Date()) => {
    const current = new Date(date)
    const day = current.getDay()
    const diffToMonday = current.getDate() - day + (day === 0 ? -6 : 1)
    const monday = new Date(current.setDate(diffToMonday))
    monday.setHours(0, 0, 0, 0)
    const sunday = new Date(monday)
    sunday.setDate(monday.getDate() + 6)
    sunday.setHours(23, 59, 59, 999)
    return {
        start: dayjs(monday).toISOString().replaceAll('Z', ''),
        end: dayjs(sunday).toISOString().replaceAll('Z', '')
    }
}

const getMonthRange = (date = new Date()) => {
    const year = date.getFullYear()
    const month = date.getMonth()
    const firstDay = new Date(year, month, 1)
    firstDay.setHours(0, 0, 0, 0)
    const lastDay = new Date(year, month + 1, 0)
    lastDay.setHours(0, 0, 0, 0)
    return {
        start: firstDay.toISOString().replaceAll('Z', ''),
        end: lastDay.toISOString().replaceAll('Z', '')
    }
}

const weekRange = getWeekRange()
const monthRange = getMonthRange()

const { data: topProductsWeek, status: topProductsWeekStatus } = useAsyncData(() =>
    getApiDashboardProductPerformance({ start: weekRange.start, end: weekRange.end, limit: 2 }))
const { data: topProductsMonth, status: topProductsMonthStatus } = useAsyncData(() =>
    getApiDashboardProductPerformance({ start: monthRange.start, end: monthRange.end, limit: 2 }))
const { data: worstProductsWeek, status: worstProductsWeekStatus } = useAsyncData(() =>
    getApiDashboardProductPerformance({ start: weekRange.start, end: weekRange.end, limit: 2, isAscending: false }))
const { data: worstProductsMonth, status: worstProductsMonthStatus } = useAsyncData(() =>
    getApiDashboardProductPerformance({ start: monthRange.start, end: monthRange.end, limit: 2, isAscending: false }))

const { data: AOVToday, status: AOVTodayStatus } = useAsyncData(() => getApiDashboardAverageOrderValueSingle())
const { data: AOVWeek, status: AOVWeekStatus } = useAsyncData(() => getApiDashboardAverageOrderValueSingle({ start: weekRange.start, end: weekRange.end }))
const { data: AOVMonth, status: AOVMonthStatus } = useAsyncData(() => getApiDashboardAverageOrderValueSingle({ start: monthRange.start, end: monthRange.end }))

const { data: sales, status: salesStatus } = useAsyncData(() => getApiDashboardAverageOrderValue({ start: monthRange.start, end: monthRange.end }))

</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Dashboard">
            <div class="card bg-base-100 shadow-xl">
                <div class="card-body grid grid-cols-1 lg:grid-cols-3">
                    <div class="stats bg-base-200 shadow">
                        <div v-if="AOVTodayStatus == 'success'" class="stat">
                            <div class="stat-title">Ventas de hoy</div>
                            <div class="stat-value">${{ AOVToday.totalAmount ?? 0 }}</div>
                            <div class="stat-desc">{{ AOVToday.ticketCount }} Tickets</div>
                            <div class="stat-desc">Ticket promedio: ${{ AOVToday.averageAmount ?? 0 }}</div>
                            <div class="stat-desc text-right">{{ dayjs().format('DD/MM/YYYY') }}</div>
                        </div>
                        <div v-else class="grid place-items-center">
                            <span class="loading loading-ring loading-xl"></span>
                        </div>
                    </div>
                    <div class="stats bg-base-200 shadow">
                        <div v-if="AOVWeekStatus == 'success'" class="stat">
                            <div class="stat-title">Ventas de la semana</div>
                            <div class="stat-value">${{ AOVWeek.totalAmount ?? 0 }}</div>
                            <div class="stat-desc">{{ AOVWeek.ticketCount }} Tickets</div>
                            <div class="stat-desc">Ticket promedio: ${{ AOVWeek.averageAmount ?? 0 }}</div>
                            <div class="stat-desc text-right">
                                {{ dayjs(weekRange.start).format('DD/MM/YYYY') }}
                                -
                                {{ dayjs(weekRange.end).format('DD/MM/YYYY') }}
                            </div>
                        </div>
                        <div v-else class="grid place-items-center">
                            <span class="loading loading-ring loading-xl"></span>
                        </div>
                    </div>
                    <div class="stats bg-base-200 shadow">
                        <div v-if="AOVMonthStatus == 'success'" class="stat">
                            <div class="stat-title">Ventas del mes</div>
                            <div class="stat-value">${{ AOVMonth.totalAmount ?? 0 }}</div>
                            <div class="stat-desc">{{ AOVMonth.ticketCount }} Tickets</div>
                            <div class="stat-desc">Ticket promedio: ${{ AOVMonth.averageAmount ?? 0 }}</div>
                            <div class="stat-desc text-right">
                                {{ dayjs(monthRange.start).format('DD/MM/YYYY') }}
                                -
                                {{ dayjs(monthRange.end).format('DD/MM/YYYY') }}
                            </div>
                        </div>
                        <div v-else class="grid place-items-center">
                            <span class="loading loading-ring loading-xl"></span>
                        </div>
                    </div>
                    <div class=" lg:col-span-3">
                        <AdminSalesChart v-if="salesStatus == 'success'" :data="sales" />
                        <div v-else class="grid place-items-center">
                            <span class="loading loading-ring loading-xl"></span>
                        </div>
                    </div>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>