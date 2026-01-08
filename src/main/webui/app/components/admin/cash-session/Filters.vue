<script setup>
const { customFilters, setParam } = useParams('adminGetApiCashSessionsParams')

const isLgScreen = useMediaQuery('(min-width: 1024px)')
const detailsRef = useTemplateRef('details')

const route = useRoute()

const onSubmitFilters = (e) => {
    detailsRef.value.removeAttribute('open')
    setParam({
        id: e.target.search_id.value,
        openedBy: e.target.search_openedBy.value,
        closedBy: e.target.search_closedBy.value,
        openingDate: e.target.search_openingDate.value,
        closingDate: e.target.search_closingDate.value,
        negatives: e.target.search_is_enabled.checked,
        status: e.target.search_status.value,
    })
}

watchEffect(() => isLgScreen.value
    ? detailsRef.value?.setAttribute('open', true)
    : detailsRef.value?.removeAttribute('open'))
</script>

<template>
    <details ref="details" :class="{ 'collapse bg-base-100 border-base-300 border': !isLgScreen }" open>
        <summary class="lg:hidden collapse-title flex justify-between font-semibold">
            Filters
            <div v-if="customFilters.length" class="badge badge-info">
                {{ customFilters.length }}
            </div>
        </summary>
        <div :class="{ 'collapse-content': !isLgScreen }">
            <form class="flex flex-col lg:grid lg:grid-cols-4 items-end gap-2 mb-3" @submit.prevent="onSubmitFilters">
                <div class="fieldset flex-1 p-0 w-full">
                    <legend class="fieldset-legend">ID</legend>
                    <input type="text" class="input w-full" name="search_id" :value="route.query.id" />
                </div>
                <div class="fieldset flex-1 p-0 w-full">
                    <legend class="fieldset-legend">Abierto por</legend>
                    <input type="text" class="input w-full" name="search_openedBy" />
                </div>
                <div class="fieldset flex-1 p-0 w-full">
                    <legend class="fieldset-legend">Cerrado por</legend>
                    <input type="text" class="input w-full" name="search_closedBy" />
                </div>
                <div class="fieldset flex-1 p-0 w-full">
                    <legend class="fieldset-legend">Fecha de apertura</legend>
                    <input type="date" class="input w-full" name="search_openingDate" />
                </div>
                <div class="fieldset flex-1 p-0 w-full">
                    <legend class="fieldset-legend">Fecha de cierre</legend>
                    <input type="date" class="input w-full" name="search_closingDate" />
                </div>
                <fieldset class="fieldset flex-1 p-0 w-full">
                    <legend class="fieldset-legend">Estatus</legend>
                    <select class="select w-full" name="search_status">
                        <option value="" selected="true">Seleccione un estado</option>
                        <option value="OPEN">Abierto</option>
                        <option value="CLOSED">Cerrado</option>
                    </select>
                </fieldset>
                <fieldset class="fieldset flex-none p-0 not-lg:w-full">
                    <legend class="fieldset-legend mb-2">Solo saldos negativos</legend>
                    <label class="label justify-center">
                        <input type="checkbox" class="toggle toggle-success" name="search_is_enabled" />
                    </label>
                </fieldset>
                <button class="btn btn-primary flex-none px-10 not-lg:w-full">
                    <Icon name="material-symbols:search" class="text-xl" />
                    Buscar
                </button>
            </form>
        </div>
    </details>
</template>