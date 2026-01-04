<script setup>
const { setParam } = useParams('adminGetApiUsersParams')

const onSubmitFilters = (e) => {
    setParam({
        id: e.target.search_id.value,
        name: e.target.search_name.value,
        status: e.target.search_status.value,
        role: e.target.search_role.value,
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
            <legend class="fieldset-legend">Nombre</legend>
            <input type="text" class="input w-full" name="search_name" />
        </div>
        <fieldset class="fieldset flex-1 p-0">
            <legend class="fieldset-legend">Estatus</legend>
            <select class="select w-full" name="search_status">
                <option value="" selected="true">Seleccione un status</option>
                <option value="PENDING_SETUP">No configurado</option>
                <option value="ACTIVE">Activo</option>
                <option value="LOCKED">Bloqueado</option>
                <option value="DISABLED">Deshabilitado</option>
            </select>
        </fieldset>
        <fieldset class="fieldset flex-1 p-0">
            <legend class="fieldset-legend">Rol</legend>
            <select class="select w-full" name="search_role">
                <option value="" selected="true">Seleccione un rol</option>
                <option value="ADMIN">Administrador</option>
                <option value="CASHIER">Cajero</option>
                <option value="WAITER">Mesero</option>
            </select>
        </fieldset>
        <button class="btn btn-primary flex-none px-10">
            <Icon name="material-symbols:search" class="text-xl" />
            Buscar
        </button>
    </form>
</template>