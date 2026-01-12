<script setup>
import { TicketServiceType } from '~/models';
import { postApiTickets } from '~/services/ticket-resource/ticket-resource';

definePageMeta({
    middleware: ['only-cashier'],
})

postApiTickets({ serviceType: TicketServiceType.TAKE_AWAY, dinerCount: 1 })
    .then(ticket => navigateTo(`/cashier/tickets/${ticket.id}`))
</script>

<template>
    <div>
        <NuxtLayout name="cashier" title="Venta Rapida">
            <div class="grid place-items-center h-screen">
                <span class="loading loading-ring loading-xl"></span>
            </div>
        </NuxtLayout>
    </div>
</template>