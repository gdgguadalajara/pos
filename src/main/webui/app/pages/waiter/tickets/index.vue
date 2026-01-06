<script setup>
import dayjs from 'dayjs';
import { TicketServiceType, TicketStatus } from '~/models';
import { getApiUsersUserIdTickets } from '~/services/users-ticket-resource/users-ticket-resource';

definePageMeta({
    middleware: ['only-waiter'],
})

const session = useSession()

const { data: paginatedTickets } = useAsyncData('myTickets', () =>
    getApiUsersUserIdTickets(session.value.id, { status: TicketStatus.OPEN }))

const serviceToText = (service) => {
    switch (service) {
        case TicketServiceType.DELIVERY: return 'Servicio a domicilio'
        case TicketServiceType.TAKE_AWAY: return 'Para llevar'
        case TicketServiceType.DINE_IN: return 'En mesa'
    }
}
</script>

<template>
    <div>
        <NuxtLayout name="waiter" title="Tickets">
            <div class="card bg-base-200">
                <div class="card-body grid grid-cols-2 lg:grid-cols-4">
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
                    <div v-else class="card">
                        <div class="card-body">
                            <span class="text-center">Sin tickets pendientes...</span>
                        </div>
                    </div>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>