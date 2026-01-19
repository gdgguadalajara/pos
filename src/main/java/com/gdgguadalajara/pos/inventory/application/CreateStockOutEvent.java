package com.gdgguadalajara.pos.inventory.application;

import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.ingredient.model.dto.IngredientSnapshot;
import com.gdgguadalajara.pos.inventory.model.StockOutEvent;
import com.gdgguadalajara.pos.inventory.model.dto.CreateStockOutEventRequest;
import com.gdgguadalajara.pos.user.model.dto.UserSnapshot;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@ApplicationScoped
@Transactional
@AllArgsConstructor
public class CreateStockOutEvent {

    private final GetCurrentSession getCurrentSession;

    public StockOutEvent run(CreateStockOutEventRequest request) {
        var event = new StockOutEvent();
        event.product = request.product();
        event.ingredientSnapshot = new IngredientSnapshot(request.ingredient());
        event.quantity = request.quantity();
        event.currentStock = request.currentStock();
        event.userSnapshot = new UserSnapshot(getCurrentSession.run().user);
        event.persistAndFlush();
        return event;
    }
}
