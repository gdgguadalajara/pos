package com.gdgguadalajara.pos.product.application;

import java.util.UUID;

import com.gdgguadalajara.pos.category.model.Category;
import com.gdgguadalajara.pos.product.model.Product;
import com.gdgguadalajara.pos.common.model.DomainException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class CreateProductCategory {

    public Product run(UUID productId, UUID categoryId) {
        var category = Category.<Category>findById(categoryId);
        var product = Product.<Product>findById(productId);
        if (category == null)
            throw DomainException.notFound("Categoria no encontrada");
        if (product == null)
            throw DomainException.notFound("Producto no encontrado");
        category.products.add(product);
        category.persistAndFlush();
        return product;
    }

}
