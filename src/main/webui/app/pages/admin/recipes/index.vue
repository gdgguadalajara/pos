<script setup>
import dayjs from 'dayjs'
import { deleteApiRecipesId, getApiRecipes } from '~/services/recipe-resource/recipe-resource';

definePageMeta({
    middleware: ['only-admin'],
})

const toast = useToast()

const {
    data: paginatedRecipes,
    status,
    refresh,
} = useLazyAsyncData(() => getApiRecipes(),
    { default: () => [] })

const deleteRecipe = (recipe) =>
    deleteApiRecipesId(recipe.id)
        .then(_ => refresh())
        .then(_ => closeModal(`delete_${recipe.id}_modal`))
        .then(_ => toast.info({ title: "Receta eliminada" }))
        .catch(_ => toast.error({ title: "Error al eliminar receta" }))
</script>

<template>
    <div>
        <NuxtLayout name="admin" title="Recetas">
            <div class="card bg-base-200 shadow-xl">
                <div class="card-body">
                    <div class="mb-3">
                        <NuxtLink to="/admin/recipes/new" class="btn btn-primary">
                            Nuevo receta
                        </NuxtLink>
                    </div>
                    <div class="overflow-x-auto rounded-box border border-base-content/5 bg-base-100">
                        <table class="table table-zebra">
                            <thead class="bg-base-200">
                                <tr>
                                    <th>#</th>
                                    <th>Receta</th>
                                    <th>Producto</th>
                                    <th>Descripción</th>
                                    <th>Creación</th>
                                    <th>Ultima modificación</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-if="status == 'pending'">
                                    <th colspan="8">
                                        <div class="grid place-items-center">
                                            <span class="loading loading-ring loading-xl"></span>
                                        </div>
                                    </th>
                                </tr>
                                <tr v-if="status == 'success'" v-for="recipe in paginatedRecipes.data" :key="recipe.id">
                                    <td>
                                        <button class="btn btn-link" @click="copy(recipe.id, 'ID copiado')">
                                            {{ recipe.id.slice(0, 8) }}...
                                        </button>
                                    </td>
                                    <td>{{ recipe.name }}</td>
                                    <td>
                                        <NuxtLink :to="`/admin/products/${recipe.product.id}`" target="_blank"
                                            class="btn btn-link">
                                            {{ recipe.product.name }}
                                        </NuxtLink>
                                    </td>
                                    <td>
                                        <div class="tooltip" :data-tip="recipe.description">
                                            {{ recipe.description.slice(0, 9) }}...
                                        </div>
                                    </td>
                                    <td>{{ dayjs(recipe.createdAt).format('DD/MM/YYYY') }}</td>
                                    <td>{{ dayjs(recipe.updatedAt).format('DD/MM/YYYY') }}</td>
                                    <td>
                                        <div class="flex gap-1">
                                            <AdminRecipesShowIngredientesTableAction :recipeId="recipe.id" />
                                            <button class="btn btn-outline btn-sm btn-error tooltip" data-tip="Eliminar"
                                                @click="openModal(`delete_${recipe.id}_modal`)">
                                                <Icon name="material-symbols:delete-outline" class="text-xl" />
                                            </button>
                                            <dialog :id="`delete_${recipe.id}_modal`" class="modal">
                                                <div class="modal-box">
                                                    <h3 class="text-lg font-bold">
                                                        ¿Eliminar receta {{ recipe.name }}?
                                                    </h3>
                                                    <p class="py-4">
                                                        ID#{{ recipe.id }}
                                                    </p>
                                                    <div class="modal-action">
                                                        <button @click="deleteRecipe(recipe)" class="btn btn-error">
                                                            Eliminar
                                                        </button>
                                                        <form method="dialog">
                                                            <button class="btn">Cancelar</button>
                                                        </form>
                                                    </div>
                                                </div>
                                                <form method="dialog" class="modal-backdrop">
                                                    <button>close</button>
                                                </form>
                                            </dialog>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="grid place-items-center mt-4">
                        <div class="join" v-if="status == 'success'">
                            <button class="join-item btn" :class="{ 'btn-disabled': !paginatedRecipes.meta.prevPage }"
                                @click="prevPage">«</button>
                            <button class="join-item btn btn-active cursor-auto">Page {{
                                paginatedRecipes.meta.currentPage }}</button>
                            <button class="join-item btn" :class="{ 'btn-disabled': !paginatedRecipes.meta.nextPage }"
                                @click="nextPage">»</button>
                        </div>
                    </div>
                </div>
            </div>
        </NuxtLayout>
    </div>
</template>