<script setup>
definePageMeta({
    middleware: ['only-cashier'],
})
</script>

<template>
    <div>
        <NuxtLayout name="cashier" title="Mesas">
        </NuxtLayout>
    </div>
</template>