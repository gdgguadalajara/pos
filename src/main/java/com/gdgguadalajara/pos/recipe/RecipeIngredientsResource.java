package com.gdgguadalajara.pos.recipe;

import java.util.List;
import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.ingredient.model.Ingredient;
import com.gdgguadalajara.pos.recipe.application.AddIngredientToRecipe;
import com.gdgguadalajara.pos.recipe.application.RemoveIngredientFromRecipe;
import com.gdgguadalajara.pos.recipe.model.Recipe;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;

@Path("/api/recipes/{id}/ingredients")
@AllArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RecipeIngredientsResource {

    private final AddIngredientToRecipe addIngredientToRecipe;
    private final RemoveIngredientFromRecipe removeIngredientFromRecipe;

    @GET
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public List<Ingredient> read(UUID id) {
        var recipe = Recipe.<Recipe>findById(id);
        if (recipe == null)
            throw DomainException.notFound("Receta no encontrada");
        return recipe.ingredients.stream().toList();
    }

    @POST
    @Path("/{ingredientId}")
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public Ingredient addIngredientQuantity(UUID id, UUID ingredientId) {
        return addIngredientToRecipe.run(ingredientId, id);
    }

    @DELETE
    @Path("/{ingredientId}")
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public Ingredient removeIngredient(UUID id, UUID ingredientId) {
        return removeIngredientFromRecipe.run(ingredientId, id);
    }
}
