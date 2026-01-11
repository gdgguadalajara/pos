<script setup>
import { getApiProductionCenters } from '~/services/production-center-resource/production-center-resource'
import { getApiUsersUuid } from '~/services/user-resource/user-resource'
import {
    getApiUsersUserIdProductionCenters,
    postApiUsersUserIdProductionCentersProductionCenterId,
    deleteApiUsersUserIdProductionCentersProductionCenterId,
} from '~/services/users-production-centers-resource/users-production-centers-resource'

definePageMeta({
    middleware: ['only-admin'],
})

const toast = useToast()
const route = useRoute()
const userId = route.params.id
const title = ref(userId)

const { data: user, status: userStatus } =
    useLazyAsyncData(`getApiUsersUuid-${userId}`, () => getApiUsersUuid(userId))

const { data: userCenters, refresh: refreshUserCenters } =
    useLazyAsyncData(`getApiUsersUuidProductionCenters-${userId}`, () => getApiUsersUserIdProductionCenters(userId), {
        immediate: false,
        default: () => []
    })

const { data: centers, status: centersStatus } =
    useLazyAsyncData('getApiProductionCenters', () => getApiProductionCenters({ size: 100 }), { default: () => [] })

const isCenterInUserCenters = (center) => userCenters.value.map(c => c.id).includes(center.id)

const addCenter = (center) => postApiUsersUserIdProductionCentersProductionCenterId(userId, center.id)
    .then(_ => refreshUserCenters())
const removeCenter = (center) => deleteApiUsersUserIdProductionCentersProductionCenterId(userId, center.id)
    .then(_ => refreshUserCenters())

watchEffect(() => {
    if (userStatus.value != 'success') return
    title.value = `Usuario ${user.value.name}`
    refreshUserCenters()
})
</script>

<template>
    <div>
        <NuxtLayout name="admin" :title="title">
            <div v-if="userStatus != 'success'" class="grid place-items-center">
                <span class="loading loading-ring loading-xl"></span>
            </div>
            <div v-else class="card bg-base-200">
                <div class="card-body">
                    <h3 class="card-title">Centros de producción</h3>
                    <div class="divider m-0"></div>
                    <div v-if="centersStatus != 'success'" class="grid place-items-center">
                        <span class="loading loading-ring loading-xl"></span>
                    </div>
                    <div v-else class="grid grid-cols-2 lg:grid-cols-4 xl:grid-cols-5 gap-4">
                        <template v-for="center in centers.data" :key="center.id">
                            <button v-if="isCenterInUserCenters(center)"
                                class="aspect-square grid place-content-center rounded-lg bg-primary cursor-pointer"
                                @click="removeCenter(center)">
                                {{ center.name }}
                            </button>
                            <button v-else
                                class="aspect-square grid place-content-center rounded-lg bg-base-100 cursor-pointer"
                                @click="addCenter(center)">
                                {{ center.name }}
                            </button>
                        </template>
                    </div>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>