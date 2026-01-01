<script setup>
import { TicketItemStatus } from '~/models';
import { getApiTicketsId } from '~/services/ticket-resource/ticket-resource';
import { deleteApiTicketsTicketIdItemsTicketItemId, postApiTicketsTicketIdItemsProductId } from '~/services/ticket-ticket-item-resource/ticket-ticket-item-resource';

const ticket = useState('ticket')
const groupedItems = computed(() => Object.groupBy(ticket.value.items, (item) => item.originalProductId))
const keysGroupedItems = computed(() => Object.keys(groupedItems.value).sort())

const addProduct = (productUuid) => postApiTicketsTicketIdItemsProductId(ticket.value.id, productUuid)
    .then(_ => getApiTicketsId(ticket.value.id))
    .then(ticket => useState('ticket').value = ticket)

const removeProduct = (productUuid) => deleteApiTicketsTicketIdItemsTicketItemId(ticket.value.id, productUuid)
    .then(_ => getApiTicketsId(ticket.value.id))
    .then(ticket => useState('ticket').value = ticket)
</script>

<template>
    <div class="grow flex flex-col gap-1">
        <div class="card bg-base-100" v-for="key in keysGroupedItems" :key="key">
            <div class="card-body p-3">
                <div class="flex flex-row justify-between">
                    <span>{{ groupedItems?.[key]?.[0].productName }}</span>
                    <span class="flex-none">${{ groupedItems?.[key]?.[0].unitPrice }}/cu</span>
                </div>
                <div class="flex justify-between">
                    <button class="btn btn-xs"
                        :disabled="!groupedItems?.[key]?.some(item => item.status == TicketItemStatus.ADDED)"
                        @click="removeProduct(groupedItems?.[key]?.find(item => item.status == TicketItemStatus.ADDED).id)">
                        <Icon name="material-symbols:remove-rounded" class="text-2xl" />
                    </button>
                    <p class="text-center">{{ groupedItems?.[key]?.length }}</p>
                    <button class="btn btn-xs" @click="addProduct(groupedItems?.[key]?.[0].originalProductId)">
                        <Icon name="material-symbols:add-2-rounded" class="text-2xl" />
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>