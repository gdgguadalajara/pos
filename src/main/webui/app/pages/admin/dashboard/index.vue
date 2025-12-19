<script setup>
definePageMeta({
    middleware: ['only-admin'],
})
</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Dashboard">
            <WorkInProgress />
        </NuxtLayout>
    </div>
</template>