<script setup>
const { setParam } = useParams('adminGetApiProductsParams')

const onSubmitFilters = (e) => {
    const checkedBoxes = document.querySelectorAll('input[name="day_of_week"]:checked')
    const checkedValues = Array.from(checkedBoxes).map(cb => cb.value)
    setParam({
        id: e.target.search_id.value,
        name: e.target.search_name.value,
        isEnabled: e.target.search_is_enabled.value,
        availableDays: checkedValues
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
            <select class="select w-full" name="search_is_enabled">
                <option value="" selected="true">Seleccione un status</option>
                <option value="true">Habilitado</option>
                <option value="false">Inhabilitado</option>
            </select>
        </fieldset>
        <div class="dropdown flex-1">
            <div tabindex="0" role="button"
                class="h-10 w-full bg-base-100 grid items-center px-3 border rounded border-base-content/20 shadow shadow-base-content/3">
                Días
            </div>
            <ul tabindex="-1" class="dropdown-content menu bg-base-100 rounded-box z-1 p-2 shadow-sm">
                <li>
                    <fieldset class="fieldset">
                        <label class="label">
                            <input type="checkbox" class="checkbox" value="MONDAY" name="day_of_week" />
                            Lunes
                        </label>
                    </fieldset>
                </li>
                <li>
                    <fieldset class="fieldset">
                        <label class="label">
                            <input type="checkbox" class="checkbox" value="TUESDAY" name="day_of_week" />
                            Martes
                        </label>
                    </fieldset>
                </li>
                <li>
                    <fieldset class="fieldset">
                        <label class="label">
                            <input type="checkbox" class="checkbox" value="WEDNESDAY" name="day_of_week" />
                            Miercoles
                        </label>
                    </fieldset>
                </li>
                <li>
                    <fieldset class="fieldset">
                        <label class="label">
                            <input type="checkbox" class="checkbox" value="THURSDAY" name="day_of_week" />
                            Jueves
                        </label>
                    </fieldset>
                </li>
                <li>
                    <fieldset class="fieldset">
                        <label class="label">
                            <input type="checkbox" class="checkbox" value="FRIDAY" name="day_of_week" />
                            Viernes
                        </label>
                    </fieldset>
                </li>
                <li>
                    <fieldset class="fieldset">
                        <label class="label">
                            <input type="checkbox" class="checkbox" value="SATURDAY" name="day_of_week" />
                            Sabado
                        </label>
                    </fieldset>
                </li>
                <li>
                    <fieldset class="fieldset">
                        <label class="label">
                            <input type="checkbox" class="checkbox" value="SUNDAY" name="day_of_week" />
                            Domingo
                        </label>
                    </fieldset>
                </li>
            </ul>
        </div>
        <button class="btn btn-primary flex-none px-10">
            <Icon name="material-symbols:search" class="text-xl" />
            Buscar
        </button>
    </form>
</template>