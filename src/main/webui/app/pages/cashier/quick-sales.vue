<script setup>
definePageMeta({
    middleware: ['only-cashier'],
})
</script>

<template>
    <div>
        <NuxtLayout name="cashier" title="Venta Rapida">
        </NuxtLayout>
    </div>
</template>