package com.gdgguadalajara.pos.product.application;

import java.util.UUID;

import com.gdgguadalajara.pos.category.model.Category;
import com.gdgguadalajara.pos.product.model.Product;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class RemoveProductCategory {

    public Product run(UUID productId, UUID categoryId) {
        var category = Category.<Category>findById(categoryId);
        var product = Product.<Product>findById(productId);
        if (category == null)
            throw new NotFoundException("Categoria no encontrada");
        if (product == null)
            throw new NotFoundException("Producto no encontrado");
        category.products.remove(product);
        category.persistAndFlush();
        return product;
    }
}
