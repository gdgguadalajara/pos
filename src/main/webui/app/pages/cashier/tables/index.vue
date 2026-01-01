<script setup>
import { getApiFloors, getApiFloorsFloorIdTables } from '~/services/floor-resource/floor-resource';
import { putApiTablesUuidOpen } from '~/services/restaurant-table-resource/restaurant-table-resource';

definePageMeta({
    middleware: ['only-cashier'],
})

const toast = useToast()
const currentFloor = useState('cashier-current-floor', () => null)

const { data: floors } = useAsyncData('getApiFloors', () => getApiFloors())

const { data: tables, refresh: refreshTables } = useAsyncData(`getApiFloorsFloorIdTables-${currentFloor.value?.id}`,
    () => getApiFloorsFloorIdTables(currentFloor.value.id), { immediate: currentFloor })

const openTable = (table) => putApiTablesUuidOpen(table.id)
    .then(table => navigateTo(`/cashier/tickets/${table.ticket.id}`))
    .catch(err => toast.error({ title: 'Error', message: err.message }))

const openTicket = (table) => navigateTo(`/cashier/tickets/${table.ticket.id}`)

const isMyTable = (table) => table.ticket.owner.id == getCurrentSession().id

const arrangedTables = computed(() => arrangeTables(currentFloor.value, tables.value ?? []))

watch(currentFloor, () => refreshTables())
</script>

<template>
    <div>
        <NuxtLayout name="cashier" title="Mesas">
            <div class="card bg-base-200 h-full">
                <div class="card-body flex-row">
                    <div class="flex-none w-48 overflow-auto flex flex-col gap-1">
                        <h3 class="text-2xl">Pisos</h3>
                        <div class="divider m-0"></div>
                        <button @click="currentFloor = floor" class="btn w-full" v-for="floor in floors"
                            :key="floor.id">
                            {{ floor.name }}
                        </button>
                    </div>
                    <div class="divider divider-horizontal m-0"></div>
                    <div class="grow flex flex-col" v-if="!!currentFloor">
                        <div class="flex justify-between">
                            <h2 class="text-2xl">{{ currentFloor.name }}</h2>
                        </div>
                        <div class="divider m-0"></div>
                        <div class="grow grid place-items-center">
                            <div class="w-full grid gap-1" :style="{
                                'grid-template-columns': `repeat(${currentFloor.gridWidth}, 1fr)`,
                                'grid-template-rows': `repeat(${currentFloor.gridHeight}, 1fr)`,
                            }">
                                <div v-for="table in arrangedTables" class="contents">
                                    <div v-if="!table" class="aspect-square bg-base-100 rounded-lg"></div>
                                    <template v-else-if="table.status == 'AVAILABLE'">
                                        <div @click="openTable(table)"
                                            class="grid place-content-center aspect-square bg-base-100 rounded-lg border border-success cursor-pointer">
                                            {{ table.name }}
                                        </div>
                                    </template>
                                    <template v-else-if="table.status == 'OCCUPIED'">
                                        <div @click="openTicket(table)"
                                            class="grid place-content-center aspect-square bg-base-100 rounded-lg border"
                                            :class="isMyTable(table) ? 'border-primary cursor-pointer' : 'border-error'">
                                            {{ table.name }}
                                        </div>
                                    </template>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>