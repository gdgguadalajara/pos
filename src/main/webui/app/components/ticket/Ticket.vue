<script setup>

const props = defineProps(['ticket', 'isQuicksale'])

const route = useRoute()

useState('currentCategory', () => route.query.category)
const ticket = useState('ticket', () => props.ticket)

onMounted(() => {
    ticket.value = props.ticket
})
</script>

<template>
    <div class="flex h-full">
        <div class="grow card bg-base-200 h-full">
            <div class="card-body">
                <TicketCategories />
                <TicketProducts />
            </div>
        </div>
        <div class="divider divider-horizontal m-0"></div>
        <div class="flex-none h-full card bg-base-200 w-3xs">
            <div class="card-body p-3">
                <TicketItems />
                <div class="divider m-0"></div>
                <div class="flex flex-row justify-between">
                    <span>Total:</span>
                    <span>${{ ticket?.totalAmount }}</span>
                </div>
                <div class="divider m-0"></div>
                <TicketActions :isQuicksale="props.isQuicksale" />
            </div>
        </div>
    </div>
</template>