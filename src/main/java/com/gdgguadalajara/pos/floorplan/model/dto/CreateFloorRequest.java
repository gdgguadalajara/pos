package com.gdgguadalajara.pos.floorplan.model.dto;

public record CreateFloorRequest(
        String name,
        Integer levelOrder,
        Integer gridWidth,
        Integer gridHeight) {
}
