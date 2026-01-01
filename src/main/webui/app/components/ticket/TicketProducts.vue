<script setup>
import { getApiCategoriesCategoryIdProductsAvailables } from '~/services/category-product-resouce/category-product-resouce';
import { getApiTicketsId } from '~/services/ticket-resource/ticket-resource';
import { postApiTicketsTicketIdItemsProductId } from '~/services/ticket-ticket-item-resource/ticket-ticket-item-resource';

const ticket = useState('ticket')
const currentCategory = useState('currentCategory')

const { data: paginatedProducts, refresh: refreshPaginatedProducts } = useAsyncData(`getApiCategoriesCategoryIdProductsAvailables-${currentCategory}`,
    () => getApiCategoriesCategoryIdProductsAvailables(currentCategory.value, { page: 1, size: 100 }),
    { immediate: !!currentCategory.value })

watch(currentCategory, () => refreshPaginatedProducts())

const addProduct = (product) => postApiTicketsTicketIdItemsProductId(ticket.value.id, product.id)
    .then(_ => getApiTicketsId(ticket.value.id))
    .then(ticket => useState('ticket').value = ticket)
</script>

<template>
    <div class="grow card bg-base-100">
        <div
            class="card-body grid auto-rows-max grid-cols-2 lg:grid-cols-3 xl:grid-cols-5 2xl:grid-cols-8 overflow-auto">
            <div class="aspect-square grid place-content-center rounded-lg bg-base-200 cursor-pointer p-5 text-center"
                @click="addProduct(product)" v-for="product in paginatedProducts?.data" :key="product.id">
                {{ product.name }}
            </div>
        </div>
    </div>
</template>