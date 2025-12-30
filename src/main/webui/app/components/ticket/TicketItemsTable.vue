<script setup>
const { items } = defineProps(['items'])
</script>

<template>
    <div class="overflow-x-auto rounded-box border border-base-content/5 bg-base-100">
        <table class="table table-zebra">
            <thead class="bg-base-200">
                <tr>
                    <th>#</th>
                    <th>Nombre</th>
                    <th>Agregado por</th>
                    <th>Precio</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="item in items" :key="item.id">
                    <td>
                        <button class="btn btn-link" @click="copyId(item.id)">
                            {{ item.id.slice(0, 8) }}...
                        </button>
                    </td>
                    <td>{{ item.productName }}</td>
                    <td>{{ item.author.name }}</td>
                    <td>${{ item.unitPrice }}</td>
                    <td>{{ item.status }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>