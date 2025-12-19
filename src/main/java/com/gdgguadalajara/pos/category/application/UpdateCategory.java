package com.gdgguadalajara.pos.category.application;

import java.util.UUID;

import com.gdgguadalajara.pos.category.model.Category;
import com.gdgguadalajara.pos.category.model.dto.UpdateCategoryRequest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UpdateCategory {

    public Category run(UUID uuid, UpdateCategoryRequest request) {
        var category = Category.<Category>findById(uuid);
        if (category == null)
            throw new NotFoundException("Category not found");
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
