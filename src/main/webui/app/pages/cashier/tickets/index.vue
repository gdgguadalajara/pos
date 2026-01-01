<script setup>
import { TicketStatus } from '~/models';
import { getApiUsersUserIdTickets } from '~/services/users-ticket-resource/users-ticket-resource';

definePageMeta({
    middleware: ['only-cashier'],
})

const { data: paginatedTickets } = useAsyncData('myTickets', () =>
    getApiUsersUserIdTickets(getCurrentSession().id, { status: TicketStatus.OPEN }))
</script>

<template>
    <div>
        <NuxtLayout name="cashier" title="Tickets">
            <div class="card bg-base-200">
                <div class="card-body grid grid-cols-3">
                    <NuxtLink v-if="paginatedTickets?.data.length" :to="`/cashier/tickets/${ticket.id}`"
                        class="card bg-base-100 cursor-pointer" v-for="ticket in paginatedTickets?.data"
                        :key="ticket.id">
                        <div class="card-body">
                            <span class="text-center">#{{ ticket.id }}
                            </span>
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