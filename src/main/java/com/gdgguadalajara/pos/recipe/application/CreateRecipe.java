package com.gdgguadalajara.pos.recipe.application;

import com.gdgguadalajara.pos.recipe.model.Recipe;
import com.gdgguadalajara.pos.recipe.model.dto.CreateRecipeRequest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class CreateRecipe {
    
    public Recipe run(CreateRecipeRequest request) {
        Recipe recipe = new Recipe();
        recipe.name = request.name();
        recipe.description = request.description();
        recipe.persist();
        return recipe;
    }
}
