package com.gdgguadalajara.pos.floorplan.model.dto;

import java.util.UUID;

import com.gdgguadalajara.pos.floorplan.model.RestaurantTableStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateRestaurantTableRequest(
        @NotBlank
        String name,
        @Positive
        @NotNull
        Integer posX,
        @Positive
        @NotNull
        Integer posY,
        @NotNull
        RestaurantTableStatus status,
        @NotNull
        UUID flooUuid) {
}
