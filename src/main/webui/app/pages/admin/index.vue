<script setup>
definePageMeta({
    middleware: ['only-admin'],
})
</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Admin">
        </NuxtLayout>
    </div>
</template>