<script setup>
const { setParam } = useParams('adminGetApiCashSessionsParams')

const route = useRoute()

const onSubmitFilters = (e) => {
    setParam({
        id: e.target.search_id.value,
        openedBy: e.target.search_openedBy.value,
        closedBy: e.target.search_closedBy.value,
        openingDate: e.target.search_openingDate.value,
        closingDate: e.target.search_closingDate.value,
        negatives: e.target.isEnabled.checked,
    })
}
</script>

<template>
    <form class="flex items-end gap-2 mb-3" @submit.prevent="onSubmitFilters">
        <div class="fieldset flex-1 p-0">
            <legend class="fieldset-legend">ID</legend>
            <input type="text" class="input w-full" name="search_id" :value="route.query.id" />
        </div>
        <div class="fieldset flex-1 p-0">
            <legend class="fieldset-legend">Abierto por</legend>
            <input type="text" class="input w-full" name="search_openedBy" />
        </div>
        <div class="fieldset flex-1 p-0">
            <legend class="fieldset-legend">Cerrado por</legend>
            <input type="text" class="input w-full" name="search_closedBy" />
        </div>
        <div class="fieldset flex-1 p-0">
            <legend class="fieldset-legend">Fecha de apertura</legend>
            <input type="date" class="input w-full" name="search_openingDate" />
        </div>
        <div class="fieldset flex-1 p-0">
            <legend class="fieldset-legend">Fecha de cierre</legend>
            <input type="date" class="input w-full" name="search_closingDate" />
        </div>
        <fieldset class="fieldset">
            <legend class="fieldset-legend mb-2">Solo saldos negativos</legend>
            <label class="label justify-center">
                <input type="checkbox" class="toggle toggle-success" name="isEnabled" />
            </label>
        </fieldset>
        <button class="btn btn-primary flex-none px-10">
            <Icon name="material-symbols:search" class="text-xl" />
            Buscar
        </button>
    </form>
</template>