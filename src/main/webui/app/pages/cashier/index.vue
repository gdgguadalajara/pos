<script setup>
definePageMeta({
    middleware: ['only-cashier'],
})
navigateTo('/cashier/tables')
</script>

<template>
    <div>
        <NuxtLayout name="cashier" title="Cashier">
        </NuxtLayout>
    </div>
</template>