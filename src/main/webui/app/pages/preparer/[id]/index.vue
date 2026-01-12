<script setup>
import { getApiProductionCentersUuid, getGetApiProductionCentersUuidStreamUrl } from '~/services/production-center-resource/production-center-resource'
import { getApiProductionCentersCenterUuidItems } from '~/services/production-center-ticket-items-resource/production-center-ticket-items-resource'
import dayjs from "dayjs"
import { TicketItemStatus, TicketServiceType } from '~/models'
import { putApiTicketItemUuidPreparing, putApiTicketItemUuidReady } from '~/services/ticket-item-resource/ticket-item-resource'

definePageMeta({
    middleware: ['only-preparer'],
})

const toast = useToast()
const route = useRoute()
const productionCenterId = route.params.id

const title = ref('Centro de preparacion')
const refreshItemsInterval = ref(null)
const eventSource = ref(null)

const { data: center } = useAsyncData(`getApiProductionCentersUuid-${productionCenterId}`,
    () => getApiProductionCentersUuid(productionCenterId))

const {
    data: productionCenterItems,
    refresh: refreshProductionCenterItems
} = useAsyncData(`getApiProductionCentersCenterUuidItems-${productionCenterId}`,
    () => getApiProductionCentersCenterUuidItems(productionCenterId),
    { immediate: center.value })

const connect = () => {
    const _eventSource = new EventSource(getGetApiProductionCentersUuidStreamUrl(productionCenterId))
    _eventSource.onmessage = _ => refreshProductionCenterItems()
    eventSource.value = _eventSource
}

const serviceToText = (service) => {
    switch (service) {
        case TicketServiceType.DELIVERY: return 'Servicio a domicilio'
        case TicketServiceType.TAKE_AWAY: return 'Para llevar'
        case TicketServiceType.DINE_IN: return 'En mesa'
    }
}

const preparing = (item) => putApiTicketItemUuidPreparing(item.id)
    .then(_ => refreshProductionCenterItems())
    .then(_ => toast.info({
        title: 'Item en preparacion',
        description: `El item ${item.productName} ha sido marcado como en preparacion.`,
        duration: 3000,
        position: 'topRight',
    }))

const ready = (item) => putApiTicketItemUuidReady(item.id)
    .then(_ => refreshProductionCenterItems())
    .then(_ => toast.success({
        title: 'Item listo',
        description: `El item ${item.productName} ha sido marcado como listo.`,
        duration: 3000,
        position: 'topRight',
    }))

watch(center, () => {
    if (center.value == null) return
    title.value = `Centro de preparacion ${center.value.name}`
    connect()
})

onMounted(() => {
    refreshItemsInterval.value = setInterval(() => {
        refreshProductionCenterItems()
    }, 5000)
})

onUnmounted(() => {
    if (eventSource.value != null)
        eventSource.value.close()
    if (refreshItemsInterval.value != null)
        clearInterval(refreshItemsInterval.value)
})
</script>

<template>
    <div>
        <NuxtLayout name="preparer" :title="title">
            <div class="grid grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
                <div v-for="{ ticketId, tableId, tableName, serviceType, item } in productionCenterItems" :key="item.id"
                    class="card bg-base-200 shadow-xl m-5">
                    <div class="card-body">
                        <p class="text-xs">{{ ticketId }}</p>
                        <p v-if="item.status == TicketItemStatus.ORDERED"
                            class="flex gap-1 badge justify-center px-5 py-3" :class="{
                                'badge-success': dayjs(item.createdAt).isAfter(dayjs().subtract(25, 'minute')),
                                'badge-warning': dayjs(item.createdAt).isBetween(dayjs().subtract(40, 'minute'), dayjs().subtract(25, 'minute')),
                                'badge-error': dayjs(item.createdAt).isBefore(dayjs().subtract(40, 'minute'))
                            }">
                            <Icon name="material-symbols:nest-clock-farsight-analog-outline-rounded" class="text-2xl">
                            </Icon>
                            {{ dayjs(item.createdAt).fromNow() }}
                        </p>
                        <p v-if="item.status == TicketItemStatus.PREPARING"
                            class="flex gap-1 badge justify-center px-5 py-3 badge-info">
                            <Icon name="material-symbols:timer" class="text-2xl"></Icon>
                            En proceso
                        </p>
                        <p class="font-bold text-2xl">{{ item.productName }}</p>
                        <p v-if="item.isTakeAway">Para llevar</p>
                        <p v-else-if="serviceType == TicketServiceType.DINE_IN">{{ tableName }}</p>
                        <p v-else>{{ serviceToText(serviceType) }}</p>
                        <button v-if="item.status == TicketItemStatus.ORDERED" class="btn btn-primary"
                            @click="preparing(item)">
                            Empezar a preparar
                        </button>
                        <button v-else class="btn btn-success" @click="ready(item)">
                            Listo
                        </button>
                        <p class="text-right">{{ dayjs(item.createdAt).format('DD/MM/YYYY HH:mm:ss') }}</p>
                    </div>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>