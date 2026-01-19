<script setup>
import { IngredientUnitType } from '~/models';
import { getApiIngredients } from '~/services/ingredient-resource/ingredient-resource';

definePageMeta({
    middleware: ['only-admin'],
})

const {
    data: paginatedIngredients,
    status,
} = useLazyAsyncData(() => getApiIngredients(),
    { default: () => [] })

const unitToText = (unit) => {
    switch (unit) {
        case IngredientUnitType.TON: return "Toneladas"
        case IngredientUnitType.KG: return "Kiligramos"
        case IngredientUnitType.G: return "Gramos"
        case IngredientUnitType.MG: return "Miligramo"
        case IngredientUnitType.L: return "Litros"
        case IngredientUnitType.ML: return "Mililitros"
        case IngredientUnitType.PIECE: return "Pieza"
        default: return unit
    }
}
</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Inventario">
            <div class="card bg-base-200 shadow-xl">
                <div class="card-body">
                    <div class="mb-3">
                        <NuxtLink to="/admin/inventory/new" class="btn btn-primary">
                            Nuevo artículo
                        </NuxtLink>
                    </div>
                    <div class="overflow-x-auto rounded-box border border-base-content/5 bg-base-100">
                        <table class="table table-zebra">
                            <thead class="bg-base-200">
                                <tr>
                                    <th>#</th>
                                    <th>Nombre</th>
                                    <th>Unidad de compra</th>
                                    <th>Unidad de uso</th>
                                    <th>Existencias en sistema</th>
                                    <th>Costo por unidad</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-if="status == 'pending'">
                                    <th colspan="8">
                                        <div class="grid place-items-center">
                                            <span class="loading loading-ring loading-xl"></span>
                                        </div>
                                    </th>
                                </tr>
                                <tr v-if="status == 'success'" v-for="ingredient in paginatedIngredients.data"
                                    :key="ingredient.id">
                                    <td>
                                        <button class="btn btn-link" @click="copy(ingredient.id, 'ID copiado')">
                                            {{ ingredient.id.slice(0, 8) }}...
                                        </button>
                                    </td>
                                    <td>{{ ingredient.name }}</td>
                                    <td>{{ unitToText(ingredient.purchaseUnit) }}</td>
                                    <td>{{ unitToText(ingredient.usageUnit) }}</td>
                                    <td>{{ ingredient.systemStock }}</td>
                                    <td>${{ ingredient.costPerPurchaseUnit }}</td>
                                    <td>
                                        <div class="flex gap-1">
                                            <!-- <NuxtLink :to="`/#/`"
                                                class="tooltip btn btn-sm btn-primary btn-outline"
                                                data-tip="Centros de produccion">
                                                <Icon name="material-symbols:factory-outline" class="text-xl" />
                                            </NuxtLink> -->
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="grid place-items-center mt-4">
                        <div class="join" v-if="status == 'success'">
                            <button class="join-item btn"
                                :class="{ 'btn-disabled': !paginatedIngredients.meta.prevPage }"
                                @click="prevPage">«</button>
                            <button class="join-item btn btn-active cursor-auto">Page {{
                                paginatedIngredients.meta.currentPage }}</button>
                            <button class="join-item btn"
                                :class="{ 'btn-disabled': !paginatedIngredients.meta.nextPage }"
                                @click="nextPage">»</button>
                        </div>
                    </div>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>