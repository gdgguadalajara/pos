package com.gdgguadalajara.pos.recipe.application;

import java.util.UUID;

import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.recipe.model.Recipe;
import com.gdgguadalajara.pos.recipe.model.dto.UpdateRecipeRequest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class UpdateRecipe {
    
    public Recipe run(UUID id, UpdateRecipeRequest request) {
        Recipe recipe = Recipe.findById(id);
        if (recipe == null) 
            throw DomainException.notFound("Receta no encontrada");
        recipe.name = request.name();
        recipe.description = request.description();
        recipe.persist();
        return recipe;
    }
}
