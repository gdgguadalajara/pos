<script setup>
definePageMeta({
    middleware: ['only-waiter'],
})
</script>

<template>
    <div>
        <NuxtLayout name="waiter" title="Dashboard">
            <WorkInProgress />
        </NuxtLayout>
    </div>
</template>