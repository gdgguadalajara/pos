package com.gdgguadalajara.pos.productioncenter.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateProductionCenterRequest(
        @NotBlank
        String name,
        @NotEmpty
        String description,
        @NotNull
        Boolean isActive) {

}
