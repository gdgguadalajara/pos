package com.gdgguadalajara.pos.ingredient.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gdgguadalajara.pos.ingredient.model.Ingredient;
import com.gdgguadalajara.pos.ingredient.model.IngredientUnitType;
import com.gdgguadalajara.pos.recipe.model.Recipe;

public record IngredientSnapshot(
        UUID id,
        String name,
        IngredientUnitType purchaseUnit,
        IngredientUnitType usageUnit,
        Double systemStock,
        Double costPerPurchaseUnit,
        RecipeSnapshot recipe) {

    public IngredientSnapshot(Ingredient ingredient) {
        this(ingredient.id, ingredient.name, ingredient.purchaseUnit,
                ingredient.usageUnit, ingredient.systemStock,
                ingredient.costPerPurchaseUnit, new RecipeSnapshot(ingredient.recipe));
    }

    public record RecipeSnapshot(
            UUID id,
            String name,
            String description,
            LocalDateTime createdAt) {
        public RecipeSnapshot(Recipe recipe) {
            this(recipe.id, recipe.name, recipe.description, recipe.createdAt);
        }
    }
}
