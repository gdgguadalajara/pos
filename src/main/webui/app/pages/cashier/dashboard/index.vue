<script setup>
definePageMeta({
    middleware: ['only-cashier'],
})
</script>

<template>
    <div>
        <NuxtLayout name="cashier" title="Dashboard">
            <WorkInProgress />
        </NuxtLayout>
    </div>
</template>