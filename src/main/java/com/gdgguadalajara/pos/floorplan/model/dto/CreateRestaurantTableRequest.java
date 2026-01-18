package com.gdgguadalajara.pos.floorplan.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateRestaurantTableRequest(
                @NotBlank
                String name,
                @NotNull
                Integer posX,
                @NotNull
                Integer posY) {
}
