<script setup>
definePageMeta({
    middleware: ['only-admin'],
})
</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Mapa de mesas">
            <WorkInProgress />
        </NuxtLayout>
    </div>
</template>