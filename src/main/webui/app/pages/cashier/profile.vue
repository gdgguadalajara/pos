<script setup>
definePageMeta({
    middleware: ['only-cashier'],
})
</script>

<template>
    <div>
        <NuxtLayout name="cashier" title="Perfil">
        </NuxtLayout>
    </div>
</template>