<script setup>
import { postApiCashSessionsIdExpenses } from '~/services/cash-session-expense-resource/cash-session-expense-resource'

definePageMeta({
    middleware: ['only-cashier'],
})

const cashSessionState = useState('cashSession')

const toast = useToast()

const onSubmitCreateExpense = (e) => {
    postApiCashSessionsIdExpenses(cashSessionState.value.id, {
        amount: +e.target.amount.value,
        category: e.target.category.value,
        description: e.target.description.value,
    })
        .then(_ => e.target.reset())
        .then(_ => toast.success({ title: 'Gasto creado' }))
        .then(_ => navigateTo("/cashier/expenses"))
        .catch(_ => toast.error({ title: 'Error al crear la categoria' }))
}
</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Nuevo Gasto">
            <div class="card bg-base-200 shadow-xl">
                <div class="card-body">
                    <form @submit.prevent="onSubmitCreateExpense" class="grid grid-cols-1 lg:grid-cols-2 gap-2">
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Categoria</legend>
                            <select class="select w-full" name="category">
                                <option value="" selected="true">Seleccione una categoria</option>
                                <option value="SUPPLIES">Suministros</option>
                                <option value="CLEANING">Limpieza</option>
                                <option value="MAINTENANCE">Mantenimiento</option>
                                <option value="WAGES">Salarios</option>
                                <option value="OTHER">Otros</option>
                            </select>
                        </fieldset>
                        <fieldset class="fieldset">
                            <legend class="fieldset-legend">Monto</legend>
                            <input type="number" class="input w-full" name="amount" />
                        </fieldset>
                        <fieldset class="fieldset lg:col-span-2">
                            <legend class="fieldset-legend">Gasto</legend>
                            <textarea class="textarea w-full" name="description"></textarea>
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