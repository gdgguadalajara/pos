package com.gdgguadalajara.pos.featureflag.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateFeatureFlagRequest(
        @NotBlank
        String name,
        @NotNull
        Boolean enabled,
        String description) {
}
