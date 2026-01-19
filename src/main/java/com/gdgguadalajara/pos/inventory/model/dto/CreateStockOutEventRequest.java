package com.gdgguadalajara.pos.inventory.model.dto;

import com.gdgguadalajara.pos.ingredient.model.Ingredient;
import com.gdgguadalajara.pos.product.model.Product;

public record CreateStockOutEventRequest(
        Product product,
        Ingredient ingredient,
        Double quantity,
        Double currentStock) {

}
