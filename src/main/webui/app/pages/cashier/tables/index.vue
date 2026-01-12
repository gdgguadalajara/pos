<script setup>
definePageMeta({
    middleware: ['only-cashier'],
})
</script>

<template>
    <div>
        <NuxtLayout name="cashier" title="Mesas">
            <Tables />
        </NuxtLayout>
    </div>
</template>