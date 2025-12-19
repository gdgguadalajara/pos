<script setup>
definePageMeta({
    middleware: ['only-admin'],
})
</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Profile">
        </NuxtLayout>
    </div>
</template>