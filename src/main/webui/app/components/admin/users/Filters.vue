<script setup>
const { customFilters, setParam } = useParams('adminGetApiUsersParams')

const isLgScreen = useMediaQuery('(min-width: 1024px)')
const detailsRef = useTemplateRef('details')

const onSubmitFilters = (e) => {
    detailsRef.value.removeAttribute('open')
    setParam({
        id: e.target.search_id.value,
        name: e.target.search_name.value,
        status: e.target.search_status.value,
        role: e.target.search_role.value,
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
                    <input type="text" class="input w-full" name="search_id" />
                </div>
                <div class="fieldset flex-1 p-0 w-full">
                    <legend class="fieldset-legend">Nombre</legend>
                    <input type="text" class="input w-full" name="search_name" />
                </div>
                <fieldset class="fieldset flex-1 p-0 w-full">
                    <legend class="fieldset-legend">Estatus</legend>
                    <select class="select w-full" name="search_status">
                        <option value="" selected="true">Seleccione un status</option>
                        <option value="PENDING_SETUP">No configurado</option>
                        <option value="ACTIVE">Activo</option>
                        <option value="LOCKED">Bloqueado</option>
                        <option value="DISABLED">Deshabilitado</option>
                    </select>
                </fieldset>
                <fieldset class="fieldset flex-1 p-0 w-full">
                    <legend class="fieldset-legend">Rol</legend>
                    <select class="select w-full" name="search_role">
                        <option value="" selected="true">Seleccione un rol</option>
                        <option value="ADMIN">Administrador</option>
                        <option value="CASHIER">Cajero</option>
                        <option value="WAITER">Mesero</option>
                    </select>
                </fieldset>
                <button class="btn btn-primary flex-none px-10 not-lg:w-full">
                    <Icon name="material-symbols:search" class="text-xl" />
                    Buscar
                </button>
            </form>
        </div>
    </details>
</template>