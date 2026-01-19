package com.gdgguadalajara.pos.recipe;

import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.common.PageBuilder;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.common.model.PaginatedResponse;
import com.gdgguadalajara.pos.common.model.dto.PaginationRequestParams;
import com.gdgguadalajara.pos.recipe.application.CreateRecipe;
import com.gdgguadalajara.pos.recipe.application.DeleteRecipe;
import com.gdgguadalajara.pos.recipe.application.UpdateRecipe;
import com.gdgguadalajara.pos.recipe.model.Recipe;
import com.gdgguadalajara.pos.recipe.model.dto.CreateRecipeRequest;
import com.gdgguadalajara.pos.recipe.model.dto.UpdateRecipeRequest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;

@Path("/api/recipes")
@AllArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RecipeResource {

    private final CreateRecipe createRecipe;
    private final UpdateRecipe updateRecipe;
    private final DeleteRecipe deleteRecipe;

    @GET
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public PaginatedResponse<Recipe> read(@Valid PaginationRequestParams params) {
        return PageBuilder.of(Recipe.findAll(), params.page, params.size);
    }

    @POST
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public Recipe create(CreateRecipeRequest request) {
        return createRecipe.run(request);
    }

    @GET
    @Path("/{id}")
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public Recipe read(UUID id) {
        var recipe = Recipe.<Recipe>findById(id);
        if (recipe == null)
            throw DomainException.notFound("Receta no encontrada");
        return recipe;
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public Recipe update(UUID id, UpdateRecipeRequest request) {
        return updateRecipe.run(id, request);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public void update(UUID id) {
        deleteRecipe.run(id);
    }
}
