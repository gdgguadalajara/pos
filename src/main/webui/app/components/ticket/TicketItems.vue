<script setup>
import { TicketItemStatus } from '~/models';
import { getApiTicketsId } from '~/services/ticket-resource/ticket-resource';
import { deleteApiTicketsTicketIdItemsTicketItemId, postApiTicketsTicketIdItemsProductId } from '~/services/ticket-ticket-item-resource/ticket-ticket-item-resource';

const toast = useToast()
const ticket = useState('ticket')
const isTakeAway = useState('isTakeAway')
const groupedItems = computed(() => Object.groupBy(ticket.value.items, (item) => item.originalProductId))
const keysGroupedItems = computed(() => Object.keys(groupedItems.value).sort())

const addProduct = (productUuid) => postApiTicketsTicketIdItemsProductId(ticket.value.id, productUuid, { isTakeAway: isTakeAway.value })
    .then(_ => getApiTicketsId(ticket.value.id))
    .then(ticket => useState('ticket').value = ticket)
    .catch(err => toast.error({ title: err.message }))


const removeProduct = (productUuid) => deleteApiTicketsTicketIdItemsTicketItemId(ticket.value.id, productUuid)
    .then(_ => getApiTicketsId(ticket.value.id))
    .then(ticket => useState('ticket').value = ticket)
    .catch(err => toast.error({ title: err.message }))
</script>

<template>
    <div class="flex-1 flex flex-col gap-1 overflow-y-auto">
        <div class="card bg-base-100" v-for="key in keysGroupedItems" :key="key">
            <div class="card-body p-3">
                <div class="flex flex-row justify-between">
                    <span class="">{{ groupedItems?.[key]?.[0].productName }}</span>
                    <span class="flex-none ">${{ groupedItems?.[key]?.[0].unitPrice }}/cu</span>
                </div>
                <div class="flex justify-between items-center">
                    <div class="join">
                        <button class="btn btn-sm"
                            :disabled="!groupedItems?.[key]?.some(item => item.status == TicketItemStatus.ADDED)"
                            @click="removeProduct(groupedItems?.[key]?.find(item => item.status == TicketItemStatus.ADDED).id)">
                            <Icon name="material-symbols:remove-rounded" class="text-2xl" />
                        </button>
                        <p
                            class="h-8 w-full grid items-center px-3 border rounded border-base-content/20 shadow shadow-base-content/3">
                            {{ groupedItems?.[key]?.length }}
                        </p>
                        <button class="btn btn-sm" @click="addProduct(groupedItems?.[key]?.[0].originalProductId)">
                            <Icon name="material-symbols:add-2-rounded" class="text-2xl" />
                        </button>
                    </div>
                    <span class="flex-1 text-right">
                        ${{groupedItems?.[key]?.reduce((a, b) => a + b.unitPrice, 0)}}
                    </span>
                </div>
            </div>
        </div>
    </div>
</template>