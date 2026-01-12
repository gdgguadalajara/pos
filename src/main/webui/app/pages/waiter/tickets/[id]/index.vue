<script setup>
import { getApiTicketsId } from '~/services/ticket-resource/ticket-resource';

definePageMeta({
    middleware: ['only-waiter'],
})

const toast = useToast()
const route = useRoute()
const ticketId = route.params.id

const { data: ticket, status } = useAsyncData(ticketId,
    () => getApiTicketsId(ticketId))

watch(status, () => {
    if (status.value == 'error')
        return navigateTo('/waiter/tickets')
            .then(() => toast.error({ message: 'Error al cargar el ticket' }))
})
</script>

<template>
    <div>
        <NuxtLayout name="waiter" :title="`Ticket #${ticket ? ticket?.id.slice(0, 8) : 'cargando...'}`">
            <div v-if="status != 'success'" class="grid place-items-center h-screen">
                <span class="loading loading-ring loading-xl"></span>
            </div>
            <Ticket v-else :ticket="ticket" :is-quicksale="ticket.serviceType == 'TAKE_AWAY'" />
        </NuxtLayout>
    </div>
</template>