package com.gdgguadalajara.pos.floorplan.model.dto;

public record UpdateFloorRequest(
        String name,
        Integer levelOrder,
        Integer gridWidth,
        Integer gridHeight) {
}
