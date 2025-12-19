<script setup>
localStorage.removeItem(Keys.TOKEN)
localStorage.removeItem(Keys.USER)
navigateTo('/login')
</script>

<template>
    <div class="h-screen w-screen grid place-items-center">
        <span class="loading loading-ring loading-xl"></span>
    </div>
</template>