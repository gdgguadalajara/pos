package com.gdgguadalajara.pos.productioncenter.application;

import com.gdgguadalajara.pos.category.model.Category;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.product.model.Product;
import com.gdgguadalajara.pos.productioncenter.model.ProductionCenter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class DeleteProduncionCenter {

    public void run(ProductionCenter center) {
        var countCategories = Category.count("productionCenter", center);
        if (countCategories > 0)
            throw DomainException
                    .badRequest("No se puede eliminar el centro de producción porque existen categorías asociadas.");
        var countProducts = Product.count("productionCenter", center);
        if (countProducts > 0)
            throw DomainException
                    .badRequest("No se puede eliminar el centro de producción porque existen productos asociados.");
        center.delete();
    }
}
