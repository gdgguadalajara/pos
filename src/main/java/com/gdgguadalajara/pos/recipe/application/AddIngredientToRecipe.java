package com.gdgguadalajara.pos.recipe.application;

import java.util.UUID;

import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.ingredient.model.Ingredient;
import com.gdgguadalajara.pos.recipe.model.Recipe;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class AddIngredientToRecipe {

    public Ingredient run(UUID ingredientId, UUID recipeId) {
        Recipe recipe = Recipe.findById(recipeId);
        if (recipe == null)
            throw DomainException.notFound("Receta no encontrada");
        Ingredient ingredient = Ingredient.findById(ingredientId);
        if (ingredient == null)
            throw DomainException.notFound("Ingrediente no encontrado");
        recipe.ingredients.add(ingredient);
        recipe.persistAndFlush();
        return ingredient;
    }
}
