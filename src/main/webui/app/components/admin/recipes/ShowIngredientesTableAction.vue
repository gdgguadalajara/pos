<script setup>
import { IngredientUnitType } from '~/models';
import { getApiRecipesIdIngredients } from '~/services/recipe-ingredients-resource/recipe-ingredients-resource';

const { recipeId } = defineProps(['recipeId'])

const { data: ingredients, status } = useLazyAsyncData(() => getApiRecipesIdIngredients(recipeId),
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
    <button @click="openModal(`recipe_${recipeId}_ingredients`)" class="tooltip btn btn-sm btn-primary btn-outline"
        data-tip="Ingredientes">
        <Icon name="material-symbols:nutrition" class="text-xl" />
    </button>
    <dialog :id="`recipe_${recipeId}_ingredients`" class="modal">
        <div class="modal-box max-w-10/12">
            <h3 class="text-2xl font-bold">Ingredientes</h3>
            <div class="divider m-0"></div>
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
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-if="status == 'pending'">
                            <th colspan="6">
                                <div class="grid place-items-center">
                                    <span class="loading loading-ring loading-xl"></span>
                                </div>
                            </th>
                        </tr>
                        <tr v-if="status == 'success'" v-for="ingredient in ingredients" :key="ingredient.id">
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
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <form method="dialog" class="modal-backdrop">
            <button>close</button>
        </form>
    </dialog>
</template>