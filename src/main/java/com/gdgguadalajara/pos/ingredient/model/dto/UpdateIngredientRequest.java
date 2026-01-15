package com.gdgguadalajara.pos.ingredient.model.dto;

import com.gdgguadalajara.pos.ingredient.model.IngredientUnitType;

public record UpdateIngredientRequest(
        String name,
        IngredientUnitType purchaseUnit,
        IngredientUnitType usageUnit,
        Double systemStock,
        Double costPerPurchaseUnit) {

}
