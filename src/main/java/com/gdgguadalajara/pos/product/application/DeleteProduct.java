package com.gdgguadalajara.pos.product.application;

import com.gdgguadalajara.pos.product.model.Product;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeleteProduct {

    public void run(Product product) {
        product.delete();
    }
}
