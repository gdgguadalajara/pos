package com.gdgguadalajara.pos.ingredient.application;

import java.util.UUID;

import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.ingredient.model.Ingredient;
import com.gdgguadalajara.pos.ingredient.model.dto.UpdateIngredientRequest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class UpdateIngredient {
    
    public Ingredient run(UUID id, UpdateIngredientRequest request) {
        var ingredient = Ingredient.<Ingredient>findById(id);
        if (ingredient == null) 
            throw DomainException.notFound("Ingrediente no encontrado");
        ingredient.name = request.name();
        ingredient.purchaseUnit = request.purchaseUnit();
        ingredient.usageUnit = request.usageUnit();
        ingredient.systemStock = request.systemStock();
        ingredient.costPerPurchaseUnit = request.costPerPurchaseUnit();
        ingredient.persist();
        return ingredient;
    }
}
