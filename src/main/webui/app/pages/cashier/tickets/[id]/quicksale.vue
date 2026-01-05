<script setup>
import { getApiTicketsId } from '~/services/ticket-resource/ticket-resource';

definePageMeta({
    middleware: ['only-cashier'],
})

const toast = useToast()
const route = useRoute()
const ticketId = route.params.id

const { data: ticket, status } = useAsyncData(ticketId,
    () => getApiTicketsId(ticketId))

watch(status, () => {
    if (status.value == 'error')
        return navigateTo('/cashier/tickets')
            .then(() => toast.error({ message: 'Error al cargar el ticket' }))
})
</script>

<template>
    <div>
        <NuxtLayout name="cashier" :title="`Ticket #${ticket ? ticket?.id : 'cargando...'}`">
            <div v-if="status != 'success'" class="grid place-items-center h-screen">
                <span class="loading loading-ring loading-xl"></span>
            </div>
            <Ticket v-else :ticket="ticket" :isQuicksale="true" />
        </NuxtLayout>
    </div>
</template>