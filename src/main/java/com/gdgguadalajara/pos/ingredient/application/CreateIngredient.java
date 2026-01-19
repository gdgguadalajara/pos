package com.gdgguadalajara.pos.ingredient.application;

import java.util.UUID;

import com.gdgguadalajara.pos.ingredient.model.Ingredient;
import com.gdgguadalajara.pos.ingredient.model.dto.CreateIngredientRequest;
import com.gdgguadalajara.pos.recipe.model.Recipe;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class CreateIngredient {

    public Ingredient run(UUID recipeId, CreateIngredientRequest request) {
        var recipe = Recipe.<Recipe>findById(recipeId);
        return this._run(recipe, request);
    }

    public Ingredient run(CreateIngredientRequest request) {
        return this._run(null, request);
    }

    private Ingredient _run(Recipe recipe, CreateIngredientRequest request) {
        Ingredient ingredient = new Ingredient();
        ingredient.name = request.name();
        ingredient.purchaseUnit = request.purchaseUnit();
        ingredient.usageUnit = request.usageUnit();
        ingredient.systemStock = request.systemStock();
        ingredient.costPerPurchaseUnit = request.costPerPurchaseUnit();
        if (recipe != null)
            ingredient.recipes.add(recipe);
        ingredient.persistAndFlush();
        return ingredient;
    }

}
