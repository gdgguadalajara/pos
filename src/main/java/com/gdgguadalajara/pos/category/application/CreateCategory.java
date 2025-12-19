package com.gdgguadalajara.pos.category.application;

import com.gdgguadalajara.pos.category.model.Category;
import com.gdgguadalajara.pos.category.model.dto.CreateCategoryRequest;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateCategory {

    public Category run(CreateCategoryRequest request) {
        var category = new Category();
        category.name = request.name();
        category.description = request.description();
        category.isEnabled = request.isEnabled();
        category.availableFrom = request.availableFrom();
        category.availableUntil = request.availableUntil();
        category.availableFromTime = request.availableFromTime();
        category.availableUntilTime = request.availableUntilTime();
        category.availableDays = request.availableDays();
        category.persistAndFlush();
        return category;
    }
}
