<script setup>
const { setParam } = useParams('adminGetApiTicketsParams')

const route = useRoute()

const onSubmitFilters = (e) => {
    setParam({
        id: e.target.search_id.value,
        owner: e.target.search_owner.value,
        amount: e.target.search_amount.value,
        status: e.target.search_status.value,
        createdAt: e.target.search_created_at.value,
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
            <legend class="fieldset-legend">Responsable</legend>
            <input type="text" class="input w-full" name="search_owner" />
        </div>
        <div class="fieldset flex-1 p-0">
            <legend class="fieldset-legend">Monto mayor a</legend>
            <input type="number" class="input w-full" name="search_amount" />
        </div>
        <fieldset class="fieldset flex-1 p-0">
            <legend class="fieldset-legend">Estatus</legend>
            <select class="select w-full" name="search_status">
                <option value="" selected="true">Seleccione un status</option>
                <option value="OPEN">Abierto</option>
                <option value="PAID">Pagado</option>
                <option value="CANCELED">Cancelado</option>
            </select>
        </fieldset>
        <div class="fieldset flex-1 p-0">
            <legend class="fieldset-legend">Fecha de creación</legend>
            <input type="date" class="input w-full" name="search_created_at" />
        </div>
        <button class="btn btn-primary flex-none px-10">
            <Icon name="material-symbols:search" class="text-xl" />
            Buscar
        </button>
    </form>
</template>