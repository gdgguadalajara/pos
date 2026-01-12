<script setup>
const { params, customFilters, setParam } = useParams('cashierGetApiTicketsParams')

const isLgScreen = useMediaQuery('(min-width: 1024px)')
const detailsRef = useTemplateRef('details')

const onSubmitFilters = (e) => {
    if (!isLgScreen.value)
        detailsRef.value.removeAttribute('open')
    setParam({
        id: e.target.search_id.value,
        amount: e.target.search_amount.value,
        status: e.target.search_status.value,
        createdAt: e.target.search_created_at.value,
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
            <form class="flex flex-col lg:flex-row items-end gap-2 mb-3" @submit.prevent="onSubmitFilters">
                <div class="fieldset flex-1 p-0 w-full">
                    <legend class="fieldset-legend">ID</legend>
                    <input type="text" class="input w-full" name="search_id" :value="params?.id" />
                </div>
                <div class="fieldset flex-1 p-0 w-full">
                    <legend class="fieldset-legend">Monto mayor a</legend>
                    <input type="number" class="input w-full" name="search_amount" />
                </div>
                <fieldset class="fieldset flex-1 p-0 w-full">
                    <legend class="fieldset-legend">Estatus</legend>
                    <select class="select w-full" name="search_status" :value="params?.status">
                        <option value="OPEN">Abierto</option>
                        <option value="PAID">Pagado</option>
                        <option value="CANCELED">Cancelado</option>
                    </select>
                </fieldset>
                <div class="fieldset flex-1 p-0 w-full">
                    <legend class="fieldset-legend">Fecha de creación</legend>
                    <input type="date" class="input w-full" name="search_created_at" :value="params?.createdAt" />
                </div>
                <button class="btn btn-primary flex-none px-10 not-lg:w-full">
                    <Icon name="material-symbols:search" class="text-xl" />
                    Buscar
                </button>
            </form>
        </div>
    </details>
</template>