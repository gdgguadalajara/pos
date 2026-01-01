package com.gdgguadalajara.pos.product.application;

import java.util.UUID;

import com.gdgguadalajara.pos.product.model.Product;
import com.gdgguadalajara.pos.product.model.dto.UpdateProductRequest;
import com.gdgguadalajara.pos.common.model.DomainException;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UpdateProduct {

    public Product run(UUID uuid, UpdateProductRequest request) {
        var product = Product.<Product>findById(uuid);
        if (product == null)
            throw DomainException.notFound("Producto no encontrado");
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
