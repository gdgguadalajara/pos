package com.gdgguadalajara.pos.product.application;

import com.gdgguadalajara.pos.product.model.Product;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class DeleteProduct {

    public void run(Product product) {
        product.categories.forEach(category -> {
            category.products.remove(product);
            category.persistAndFlush();
        });
        product.delete();
    }
}
