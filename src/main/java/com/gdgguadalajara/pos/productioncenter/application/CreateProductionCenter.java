package com.gdgguadalajara.pos.productioncenter.application;

import com.gdgguadalajara.pos.productioncenter.model.ProductionCenter;
import com.gdgguadalajara.pos.productioncenter.model.dto.CreateProductionCenterRequest;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateProductionCenter {

    public ProductionCenter run(CreateProductionCenterRequest request) {
        var productionCenter = new ProductionCenter();
        productionCenter.name = request.name();
        productionCenter.description = request.description();
        productionCenter.isActive = request.isActive();
        productionCenter.persist();
        return productionCenter;
    }

}
