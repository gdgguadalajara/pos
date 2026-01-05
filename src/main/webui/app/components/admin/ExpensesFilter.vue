<script setup>
const { setParam } = useParams('adminGetApiExpensesParams')

const onSubmitFilters = (e) => {
    setParam({
        id: e.target.search_id.value,
        createdBy: e.target.search_name.value,
        amount: e.target.search_amount.value,
        category: e.target.search_category.value,
        createdAt: e.target.search_created_at.value,
    })
}
</script>

<template>
    <form class="flex items-end gap-2 mb-3" @submit.prevent="onSubmitFilters">
        <div class="fieldset flex-1 p-0">
            <legend class="fieldset-legend">ID</legend>
            <input type="text" class="input w-full" name="search_id" />
        </div>
        <div class="fieldset flex-1 p-0">
            <legend class="fieldset-legend">Responsable</legend>
            <input type="text" class="input w-full" name="search_name" />
        </div>
        <div class="fieldset flex-1 p-0">
            <legend class="fieldset-legend">Montos mayores a</legend>
            <input type="number" class="input w-full" name="search_amount" />
        </div>
        <fieldset class="fieldset flex-1 p-0">
            <legend class="fieldset-legend">Categoria</legend>
            <select class="select w-full" name="search_category">
                <option value="" selected="true">Seleccione una categoria</option>
                <option value="SUPPLIES">Suministros</option>
                <option value="CLEANING">Limpieza</option>
                <option value="MAINTENANCE">Mantenimiento</option>
                <option value="WAGES">Salarios</option>
                <option value="OTHER">Otros</option>
            </select>
        </fieldset>
        <div class="fieldset flex-1 p-0">
            <legend class="fieldset-legend">Fecha de creacion</legend>
            <input type="date" class="input w-full" name="search_created_at" />
        </div>
        <button class="btn btn-primary flex-none px-10">
            <Icon name="material-symbols:search" class="text-xl" />
            Buscar
        </button>
    </form>
</template>