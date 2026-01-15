<script setup>
definePageMeta({
    middleware: ['only-waiter'],
})
</script>

<template>
    <div>
        <NuxtLayout name="waiter" title="Mesas">
            <SharedTables />
        </NuxtLayout>
    </div>
</template>