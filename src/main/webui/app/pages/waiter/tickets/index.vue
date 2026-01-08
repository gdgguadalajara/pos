<script setup>
import dayjs from 'dayjs';
import { TicketServiceType, TicketStatus } from '~/models';
import { getApiUsersUserIdTickets } from '~/services/users-ticket-resource/users-ticket-resource';

definePageMeta({
    middleware: ['only-waiter'],
})

const session = useSession()

const { params, setParam } = useParams('waiterGetApiTicketsParams', { status: TicketStatus.OPEN, createdAt: dayjs().format('YYYY-MM-DD') })

const { data: paginatedTickets, refresh } = useAsyncData('myTickets', () =>
    getApiUsersUserIdTickets(session.value.id, params.value))

const serviceToText = (service) => {
    switch (service) {
        case TicketServiceType.DELIVERY: return 'Servicio a domicilio'
        case TicketServiceType.TAKE_AWAY: return 'Para llevar'
        case TicketServiceType.DINE_IN: return 'En mesa'
    }
}

watch(params, () => refresh())
onMounted(() => setParam({ status: TicketStatus.OPEN, createdAt: dayjs().format('YYYY-MM-DD') }))
</script>

<template>
    <div>
        <NuxtLayout name="waiter" title="Tickets">
            <div class="card bg-base-200">
                <div class="card-body">
                    <WaiterTicketsFilters />
                    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
                        <NuxtLink v-if="paginatedTickets?.data.length" :to="`/waiter/tickets/${ticket.id}`"
                            class="card bg-base-100 cursor-pointer" v-for="ticket in paginatedTickets?.data"
                            :key="ticket.id">
                            <div class="card-body">
                                <span class="card-title font-bold">
                                    Ticket#{{ ticket.id.slice(0, 8) }}
                                </span>
                                <p>Fecha: {{ dayjs(ticket.createdAt).format('DD/MM/YYYY HH:mm') }}</p>
                                <p>Servicio: {{ serviceToText(ticket.serviceType) }}</p>
                                <p>Comensales: {{ ticket.dinerCount }}</p>
                                <p>Productos: {{ ticket.items.length }}</p>
                                <p>Total: ${{ ticket.totalAmount }}</p>
                            </div>
                        </NuxtLink>
                    </div>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>