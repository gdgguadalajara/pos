package com.gdgguadalajara.pos.category.application;

import java.util.UUID;

import com.gdgguadalajara.pos.category.model.Category;
import com.gdgguadalajara.pos.product.model.Product;
import com.gdgguadalajara.pos.common.model.DomainException;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RemoveCategoryProduct {

    public Category run(UUID categoryId, UUID productId) {
        var category = Category.<Category>findById(categoryId);
        var product = Product.<Product>findById(productId);
        if (category == null)
            throw DomainException.notFound("Categoria no encontrada");
        if (product == null)
            throw DomainException.notFound("Producto no encontrado");
        category.products.remove(product);
        category.persistAndFlush();
        return category;
    }
}
