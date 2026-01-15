<script setup>
const { inputRef } = defineProps(['inputRef'])

const numberBtn = (value) =>
    inputRef.value = inputRef.value + value

const clear = () => inputRef.value = ''

const remove = () => inputRef.value = inputRef.value.slice(0, -1)
</script>

<template>
    <div class="flex flex-col">
        <div class="flex flex-row w-full">
            <button @click="numberBtn('1')" class="btn btn-xl flex-1" type="button">1</button>
            <button @click="numberBtn('2')" class="btn btn-xl flex-1" type="button">2</button>
            <button @click="numberBtn('3')" class="btn btn-xl flex-1" type="button">3</button>
        </div>
        <div class="flex flex-row w-full">
            <button @click="numberBtn('4')" class="btn btn-xl flex-1" type="button">4</button>
            <button @click="numberBtn('5')" class="btn btn-xl flex-1" type="button">5</button>
            <button @click="numberBtn('6')" class="btn btn-xl flex-1" type="button">6</button>
        </div>
        <div class="flex flex-row w-full">
            <button @click="numberBtn('7')" class="btn btn-xl flex-1" type="button">7</button>
            <button @click="numberBtn('8')" class="btn btn-xl flex-1" type="button">8</button>
            <button @click="numberBtn('9')" class="btn btn-xl flex-1" type="button">9</button>
        </div>
        <div class="flex flex-row w-full">
            <button @click="clear" class="btn btn-xl flex-1" type="button">
                <Icon name="material-symbols:cancel-outline" class="text-3xl" />
            </button>
            <button @click="numberBtn('0')" class="btn btn-xl flex-1" type="button">0</button>
            <button @click="remove" class="btn btn-xl flex-1" type="button">
                <Icon name="material-symbols:arrow-back-rounded" class="text-3xl" />
            </button>
        </div>
    </div>
</template>