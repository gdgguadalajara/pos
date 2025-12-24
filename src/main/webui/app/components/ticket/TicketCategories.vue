<script setup>
import { getApiCategoriesAvailables } from '~/services/category-resource/category-resource';

const router = useRouter()
const route = useRoute()

const { data: paginatedCategories } = useAsyncData('getApiCategoriesAvailables',
    () => getApiCategoriesAvailables({ page: 1, size: 100 }))

const setCategory = (category) =>
    router.push({ query: { ...route.query, category: category.id } })
        .then(_ => useState('currentCategory').value = category.id)
</script>

<template>
    <div class="flex-none card bg-base-100">
        <div class="card-body flex-row overflow-auto">
            <button class="btn" :class="{ 'btn-primary': category.id == route.query.category }"
                @click="setCategory(category)" v-for="category in paginatedCategories?.data" :key="category.id">
                {{ category.name }}
            </button>
        </div>
    </div>
</template>