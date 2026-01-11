<script setup>

const props = defineProps(['ticket', 'isQuicksale'])

const route = useRoute()
const isLgScreen = useMediaQuery('(min-width: 1024px)')

useState('currentCategory', () => route.query.category)
const isTakeAway = useState('isTakeAway', () => false)
const ticket = useState('ticket', () => props.ticket)

onMounted(() => {
    ticket.value = props.ticket
    isTakeAway.value = props.isQuicksale
})
</script>

<template>
    <div class="lg:flex h-[calc(100vh-110px)]">
        <div class="h-full grow card bg-base-200">
            <div class="card-body h-full">
                <TicketCategories />
                <div class="flex-1 overflow-auto">
                    <TicketProducts />
                </div>
            </div>
        </div>
        <div class="not-lg:hidden flex divider divider-horizontal m-0"></div>
        <div :class="[
            'card bg-base-200 outline-0 flex-none flex flex-col',
            'not-lg:collapse not-lg:absolute not-lg:left-1/12 not-lg:bottom-0 not-lg:mb-3 not-lg:w-10/12 not-lg:max-h-9/12',
            'lg:w-3xs'
        ]">
            <input type="checkbox" class="hidden" id="collapsable-ticket" />
            <label for="collapsable-ticket"
                class="flex items-center justify-center gap-2 font-bold lg:hidden collapse-title p-3 text-center bg-primary shadow-xl">
                Ticket <div class="badge badge-info font-medium">{{ ticket.items.length }}</div>
            </label>
            <div :class="['not-lg:grow lg:h-full card-body p-3', { 'collapse-content': !isLgScreen }]">
                <template v-if="isLgScreen">
                    <TicketItems />
                </template>
                <div v-else class="grow h-60 flex">
                    <TicketItems />
                </div>
                <div class="flex-none">
                    <div class="divider m-0"></div>
                    <div class="flex flex-row justify-between text-xl">
                        <span>Total:</span>
                        <span>${{ ticket?.totalAmount }}</span>
                    </div>
                    <div class="divider m-0"></div>
                    <TicketActions :is-quicksale="props.isQuicksale" />
                </div>
            </div>
        </div>
    </div>
</template>