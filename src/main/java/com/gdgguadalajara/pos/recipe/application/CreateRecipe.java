package com.gdgguadalajara.pos.recipe.application;

import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.ingredient.model.Ingredient;
import com.gdgguadalajara.pos.product.model.Product;
import com.gdgguadalajara.pos.recipe.model.Recipe;
import com.gdgguadalajara.pos.recipe.model.dto.CreateRecipeRequest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class CreateRecipe {

    public Recipe run(CreateRecipeRequest request) {
        var product = Product.<Product>findById(request.productId());
        if (product == null)
            throw DomainException.notFound("Producto no encontrado");
        var ingredients = Ingredient.<Ingredient>findByIds(request.ingredientIds());
        Recipe recipe = new Recipe();
        recipe.name = request.name();
        recipe.description = request.description();
        recipe.product = product;
        recipe.ingredients.addAll(ingredients);
        recipe.persistAndFlush();
        return recipe;
    }
}
