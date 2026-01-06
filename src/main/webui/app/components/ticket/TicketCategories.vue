<script setup>
import { getApiCategoriesAvailables } from '~/services/category-resource/category-resource';

const router = useRouter()
const route = useRoute()

const currentCategory = useState('currentCategory', () => null)

const { data: paginatedCategories } = useAsyncData('getApiCategoriesAvailables',
    () => getApiCategoriesAvailables({ page: 1, size: 100 }))

const setCategory = (category) =>
    router.push({ query: { ...route.query, category: category.id } })
        .then(_ => currentCategory.value = category.id)

watchEffect(() => {
    if (currentCategory?.value)
        router.push({ query: { ...route.query, category: currentCategory.value } })
    if (!currentCategory?.value && paginatedCategories?.value?.data?.length > 0)
        router.push({ query: { ...route.query, category: paginatedCategories?.value.data[0].id } })
            .then(_ => currentCategory.value = paginatedCategories?.value.data[0].id)
})
</script>

<template>
    <div class="flex-none card bg-base-100">
        <div class="card-body not-lg:p-3 flex-row overflow-auto">
            <button class="btn" :class="{ 'btn-primary': category.id == route.query.category }"
                @click="setCategory(category)" v-for="category in paginatedCategories?.data" :key="category.id">
                {{ category.name }}
            </button>
        </div>
    </div>
</template>