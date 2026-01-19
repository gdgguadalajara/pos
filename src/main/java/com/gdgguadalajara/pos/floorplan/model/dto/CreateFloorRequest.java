package com.gdgguadalajara.pos.floorplan.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateFloorRequest(
        @NotBlank
        String name,
        @NotNull
        Integer levelOrder,
        @Positive
        Integer gridWidth,
        @Positive
        Integer gridHeight) {
}
