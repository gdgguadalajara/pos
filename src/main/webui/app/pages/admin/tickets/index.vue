<script setup>
definePageMeta({
    middleware: ['only-admin'],
})
</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Tickets">
            <WorkInProgress />
        </NuxtLayout>
    </div>
</template>