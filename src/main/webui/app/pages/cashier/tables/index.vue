<script setup>
definePageMeta({
    middleware: ['only-cashier'],
})
</script>

<template>
    <div>
        <NuxtLayout name="cashier" title="Mesas">
            <SharedTables />
        </NuxtLayout>
    </div>
</template>