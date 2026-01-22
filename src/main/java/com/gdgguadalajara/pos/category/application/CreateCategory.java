package com.gdgguadalajara.pos.category.application;

import com.gdgguadalajara.pos.category.model.Category;
import com.gdgguadalajara.pos.category.model.dto.CreateCategoryRequest;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.productioncenter.model.ProductionCenter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class CreateCategory {

    public Category run(CreateCategoryRequest request) {
        var center = ProductionCenter.<ProductionCenter>findById(request.productionCenterId());
        if (center == null)
            throw DomainException.notFound("Production center not found");
        var category = new Category();
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
