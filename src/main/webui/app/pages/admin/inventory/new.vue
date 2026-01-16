<script setup>
import { postApiIngredients } from '~/services/ingredient-resource/ingredient-resource';

definePageMeta({
    middleware: ['only-admin'],
})

const toast = useToast()

const onSubmitNewItem = (e) => postApiIngredients({
    name: e.target.name.value,
    purchaseUnit: e.target.purchase_unit.value,
    usageUnit: e.target.usage_unit.value,
    costPerPurchaseUnit: e.target.cost_per_purchase_unit.value,
    systemStock: e.target.system_stock.value,
})
    .then(_ => toast.success({ message: 'Ingrediente creado con exito' }))
    .then(_ => navigateTo('/admin/inventory'))
    .catch(_ => toast.error({ message: 'Error al crear el ingrediente' }))
</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Nuevo artículo">
            <div class="card bg-base-200 shadow-xl">
                <div class="card-body">
                    <form @submit.prevent="onSubmitNewItem" class="grid grid-cols-1 lg:grid-cols-2 gap-2">
                        <fieldset class="fieldset lg:col-span-2">
                            <legend class="fieldset-legend">Ingrediente</legend>
                            <input type="text" class="input w-full" name="name" />
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Unidad de compra</legend>
                            <select class="select w-full" name="purchase_unit">
                                <option value="" selected="true">Seleccione unidad</option>
                                <option value="TON">Toneladas</option>
                                <option value="KG">Kiligramos</option>
                                <option value="G">Gramos</option>
                                <option value="MG">Miligramo</option>
                                <option value="L">Litros</option>
                                <option value="ML">Mililitros</option>
                                <option value="PIECE">Pieza</option>
                            </select>
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Unidad de uso</legend>
                            <select class="select w-full" name="usage_unit">
                                <option value="" selected="true">Seleccione unidad</option>
                                <option value="TON">Toneladas</option>
                                <option value="KG">Kiligramos</option>
                                <option value="G">Gramos</option>
                                <option value="MG">Miligramo</option>
                                <option value="L">Litros</option>
                                <option value="ML">Mililitros</option>
                                <option value="PIECE">Pieza</option>
                            </select>
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Existencias</legend>
                            <input type="number" name="system_stock" class="input w-full" />
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Costo por unidad de compra</legend>
                            <input type="number" name="cost_per_purchase_unit" class="input w-full" />
                        </fieldset>
                        <div class="lg:col-span-2 lg:grid lg:justify-end mt-3">
                            <button class="w-full btn btn-primary lg:w-60">Crear</button>
                        </div>
                    </form>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>