package com.gdgguadalajara.pos.inventory.application;

import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.ingredient.model.Ingredient;
import com.gdgguadalajara.pos.inventory.model.dto.AddStockRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@ApplicationScoped
@Transactional
@AllArgsConstructor
public class AddStock {

    public Ingredient run(AddStockRequest request) {
        Ingredient ingredient = Ingredient.findById(request.ingredientId());
        if (ingredient == null)
            throw DomainException.notFound("Ingredient not found with id " + request.ingredientId());
        if (ingredient.systemStock == null)
            ingredient.systemStock = 0.0;
        ingredient.systemStock += request.quantity();
        ingredient.persistAndFlush();
        return ingredient;
    }
}
