<script setup>
definePageMeta({
    middleware: ['only-waiter'],
})
navigateTo('/waiter/tables')
</script>

<template>
    <div>
        <NuxtLayout name="waiter" title="Waiter">
        </NuxtLayout>
    </div>
</template>