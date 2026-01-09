package com.gdgguadalajara.pos.productioncenter.application;

import java.util.UUID;

import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.product.model.Product;
import com.gdgguadalajara.pos.productioncenter.model.ProductionCenter;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GetEffectiveProductionCenter {

    public ProductionCenter run(UUID productId) throws DomainException {
        var product = Product.<Product>find(
                "FROM Product p " +
                        "LEFT JOIN FETCH p.productionCenter " +
                        "LEFT JOIN FETCH p.categories c " +
                        "LEFT JOIN FETCH c.productionCenter " +
                        "WHERE p.id = ?1",
                productId).firstResult();
        if (product == null)
            throw DomainException.notFound("Producto no encontrado");

        if (product.productionCenter != null)
            return product.productionCenter;

        return product.categories.stream()
                .map(category -> category.productionCenter)
                .findFirst()
                .orElseThrow(() -> DomainException.notFound(
                        "El producto no tiene centro de producción ni categorías asignadas"));
    }

}
