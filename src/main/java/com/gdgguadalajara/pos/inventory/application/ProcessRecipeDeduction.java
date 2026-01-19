package com.gdgguadalajara.pos.inventory.application;

import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.ingredient.model.Ingredient;
import com.gdgguadalajara.pos.inventory.model.dto.CreateStockOutEventRequest;
import com.gdgguadalajara.pos.product.model.Product;
import com.gdgguadalajara.pos.recipe.model.Recipe;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.UUID;

@ApplicationScoped
@Transactional
@AllArgsConstructor
public class ProcessRecipeDeduction {

    private final CreateStockOutEvent createStockOutEvent;

    public Recipe run(UUID recipeId) throws DomainException {
        Recipe recipe = Recipe.findById(recipeId);
        if (recipe == null)
            throw DomainException.notFound("Receta no encontrada");
        return this._run(recipe);
    }

    public Recipe run(Product product) throws DomainException {
        if (product.recipe == null)
            throw DomainException.notFound("Receta no encontrada");
        return this._run(product.recipe);
    }

    public Recipe run(Recipe recipe) {
        return this._run(recipe);
    }

    private Recipe _run(Recipe recipe) {
        var recipeIngredients = recipe.ingredients.stream().toList();
        if (recipeIngredients.isEmpty())
            return recipe;
        for (var ingredient : recipeIngredients) {
            var factor = Ingredient.getUsageToPurchaseFactor(ingredient);
            if (ingredient.systemStock < factor)
                this.createStockOutEvent.run(
                        new CreateStockOutEventRequest(recipe.product, ingredient,
                                factor, ingredient.systemStock));
            var newStock = ingredient.systemStock - factor;
            if(newStock < 0)
                newStock = 0.0;
            ingredient.systemStock = newStock;
            ingredient.persistAndFlush();
        }
        recipe = Recipe.findById(recipe.id);
        return recipe;
    }
}
