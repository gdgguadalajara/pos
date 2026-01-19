package com.gdgguadalajara.pos.floorplan.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateFloorRequest(
        @NotBlank
        String name,
        @NotNull
        Integer levelOrder,
        @Positive
        @NotNull
        Integer gridWidth,
        @Positive
        @NotNull
        Integer gridHeight) {
}
