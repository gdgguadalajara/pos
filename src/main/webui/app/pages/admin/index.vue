<script setup>
definePageMeta({
    middleware: ['only-admin'],
})
navigateTo('/admin/dashboard')
</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Admin">
        </NuxtLayout>
    </div>
</template>