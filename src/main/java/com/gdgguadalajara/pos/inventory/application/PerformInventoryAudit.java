package com.gdgguadalajara.pos.inventory.application;

import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.ingredient.model.Ingredient;
import com.gdgguadalajara.pos.ingredient.model.dto.IngredientSnapshot;
import com.gdgguadalajara.pos.user.model.dto.UserSnapshot;
import com.gdgguadalajara.pos.inventory.model.InventoryAudit;
import com.gdgguadalajara.pos.inventory.model.dto.CreateInventoryAuditRequest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@ApplicationScoped
@Transactional
@AllArgsConstructor
public class PerformInventoryAudit {

    private final GetCurrentSession getCurrentSession;

    public InventoryAudit run(CreateInventoryAuditRequest request) {
        var ingredient = Ingredient.<Ingredient>findById(request.ingredientId());
        if (ingredient == null)
            throw DomainException.notFound("Ingrediente no encontrado");
        var physicalStock = request.physicalStock();
        var shrinkage = ingredient.systemStock - physicalStock;
        var audit = new InventoryAudit();
        audit.date = LocalDateTime.now();
        audit.ingredientSnapshot = new IngredientSnapshot(ingredient);
        audit.userSnapshot = new UserSnapshot(getCurrentSession.run().user);
        audit.physicalStock = physicalStock;
        audit.systemStockAtMoment = ingredient.systemStock;
        audit.shrinkage = shrinkage;
        audit.persistAndFlush();
        ingredient.systemStock = physicalStock;
        ingredient.persistAndFlush();
        return audit;
    }
}
