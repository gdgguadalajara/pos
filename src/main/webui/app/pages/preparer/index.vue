<script setup>
import { getApiUsersUserIdProductionCenters } from '~/services/users-production-centers-resource/users-production-centers-resource';

definePageMeta({
    middleware: ['only-preparer'],
})

const session = useSession()

const { data: productionCenters, status } = useAsyncData('getApiUsersUserIdProductionCenters', () => getApiUsersUserIdProductionCenters(session.value.id))

</script>

<template>
    <div>
        <NuxtLayout name="preparer" title="Preparador">
            <div class="card bg-base-200 shadow-xl">
                <div class="card-body">
                    <div v-if="status == 'pending'" class="grid place-items-center">
                        <span class="loading loading-ring loading-xl"></span>
                    </div>
                    <div v-if="status == 'success'" class="grid grid-cols-2 lg:grid-cols-3">
                        <NuxtLink :to="`/preparer/${center.id}`" class="card bg-base-100 cursor-pointer"
                            v-for="center in productionCenters" :key="center.id">
                            <div class="card-body">
                                <span class="card-title font-bold">
                                    {{ center.name }}
                                </span>
                                <p class="text-xs">{{ center.id }}</p>
                            </div>
                        </NuxtLink>
                    </div>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>