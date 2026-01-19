package com.gdgguadalajara.pos.recipe.application;

import java.util.UUID;

import com.gdgguadalajara.pos.recipe.model.Recipe;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class DeleteRecipe {
    
    public void run(UUID id) {
        Recipe recipe = Recipe.findById(id);
        if (recipe == null) return;
        recipe.product.recipe = null;
        recipe.delete();
    }
}
