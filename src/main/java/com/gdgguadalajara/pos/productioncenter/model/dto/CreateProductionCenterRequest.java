package com.gdgguadalajara.pos.productioncenter.model.dto;

public record CreateProductionCenterRequest(
        String name,
        String description,
        Boolean isActive) {

}
