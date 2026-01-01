<script setup>
import { getApiFloors } from '~/services/floor-resource/floor-resource';

definePageMeta({
    middleware: ['only-admin'],
})

const currentFloor = useState('admin-current-floor', () => null)
const { data: floors } = useAsyncData('getApiFloors', () => getApiFloors())

</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Mapa de mesas">
            <div class="card bg-base-200 h-full">
                <div class="card-body flex-row">
                    <div class="flex-none w-48 flex flex-col">
                        <FloorplanCreateModal />
                        <div class="divider m-0"></div>
                        <div class="flex-1 overflow-auto flex flex-col gap-1">
                            <button @click="currentFloor = floor" class="btn w-full" v-for="floor in floors"
                                :key="floor.id">
                                {{ floor.name }}
                            </button>
                        </div>
                        <div class="divider m-0"></div>
                        <div class="flex-1 grid grid-cols-2 gap-1">
                            <FloorplanActions v-if="!!currentFloor" />
                        </div>
                    </div>
                    <div class="divider divider-horizontal m-0"></div>
                    <div class="grow flex flex-col" v-if="!!currentFloor">
                        <div class="flex justify-between">
                            <h2 class="text-2xl">{{ currentFloor.name }}</h2>
                            <div>
                                <FloorplanDeleteModal />
                            </div>
                        </div>
                        <div class="divider m-0"></div>
                        <div class="grow grid place-items-center">
                            <FloorplanTablesGrid />
                        </div>
                    </div>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>