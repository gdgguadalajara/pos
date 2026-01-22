package com.gdgguadalajara.pos.product.application;

import com.gdgguadalajara.pos.product.model.Product;
import com.gdgguadalajara.pos.product.model.dto.CreateProductRequest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class CreateProduct {

    public Product run(CreateProductRequest request) {
        var product = new Product();
        product.name = request.name();
        product.description = request.description();
        product.price = request.price();
        product.isEnabled = request.isEnabled();
        product.availableFrom = request.availableFrom();
        product.availableUntil = request.availableUntil();
        product.availableFromTime = request.availableFromTime();
        product.availableUntilTime = request.availableUntilTime();
        product.availableDays = request.availableDays();
        product.persistAndFlush();
        return product;
    }
}
