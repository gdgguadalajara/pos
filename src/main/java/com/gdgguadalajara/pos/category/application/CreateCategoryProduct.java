package com.gdgguadalajara.pos.category.application;

import java.util.UUID;

import com.gdgguadalajara.pos.category.model.Category;
import com.gdgguadalajara.pos.product.model.Product;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CreateCategoryProduct {

    public Category run(UUID categoryId, UUID productId) {
        var category = Category.<Category>findById(categoryId);
        var product = Product.<Product>findById(productId);
        if (category == null)
            throw new NotFoundException("Categoria no encontrada");
        if (product == null)
            throw new NotFoundException("Producto no encontrado");
        category.products.add(product);
        category.persistAndFlush();
        return category;
    }
}
