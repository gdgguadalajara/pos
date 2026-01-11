package com.gdgguadalajara.pos.category.application;

import java.util.UUID;

import com.gdgguadalajara.pos.category.model.Category;
import com.gdgguadalajara.pos.category.model.dto.UpdateCategoryRequest;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.productioncenter.model.ProductionCenter;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UpdateCategory {

    public Category run(UUID uuid, UpdateCategoryRequest request) {
        var category = Category.<Category>findById(uuid);
        if (category == null)
            throw DomainException.notFound("Categoria no encontrada");
        var center = ProductionCenter.<ProductionCenter>findById(request.productionCenterId());
        if (center == null)
            throw DomainException.notFound("Production center not found");
        category.name = request.name();
        category.description = request.description();
        category.isEnabled = request.isEnabled();
        category.availableFrom = request.availableFrom();
        category.availableUntil = request.availableUntil();
        category.availableFromTime = request.availableFromTime();
        category.availableUntilTime = request.availableUntilTime();
        category.availableDays = request.availableDays();
        category.productionCenter = center;
        category.persistAndFlush();
        return category;
    }
}
