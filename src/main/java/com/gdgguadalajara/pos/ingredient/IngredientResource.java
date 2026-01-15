package com.gdgguadalajara.pos.ingredient;

import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.common.PageBuilder;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.common.model.PaginatedResponse;
import com.gdgguadalajara.pos.common.model.dto.PaginationRequestParams;
import com.gdgguadalajara.pos.ingredient.application.CreateIngredient;
import com.gdgguadalajara.pos.ingredient.application.UpdateIngredient;
import com.gdgguadalajara.pos.ingredient.model.Ingredient;
import com.gdgguadalajara.pos.ingredient.model.dto.CreateIngredientRequest;
import com.gdgguadalajara.pos.ingredient.model.dto.UpdateIngredientRequest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;

@Path("/api/ingredients")
@AllArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IngredientResource {
    
    private final CreateIngredient createIngredient;
    private final UpdateIngredient updateIngredient;

    @GET
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public PaginatedResponse<Ingredient> read(@Valid PaginationRequestParams params) {
        return PageBuilder.of(Ingredient.findAll(), params.page, params.size);
    }

    @POST
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public Ingredient create(CreateIngredientRequest request) {
        return createIngredient.run(request);
    }

    @GET
    @Path("/{id}")
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public Ingredient read(UUID id) {
        var ingredient = Ingredient.<Ingredient>findById(id);
        if (ingredient == null)
            throw DomainException.notFound("Ingrediente no encontrado");
        return ingredient;
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public Ingredient update(UUID id, UpdateIngredientRequest request) {
        return updateIngredient.run(id, request);
    }

}
