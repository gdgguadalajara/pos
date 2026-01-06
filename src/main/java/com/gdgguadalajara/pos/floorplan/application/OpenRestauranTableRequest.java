package com.gdgguadalajara.pos.floorplan.application;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Positive;

public record OpenRestauranTableRequest(
        @NotNull @Positive Integer dinerCount) {

}
