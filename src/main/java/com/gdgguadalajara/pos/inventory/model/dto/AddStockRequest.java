package com.gdgguadalajara.pos.inventory.model.dto;

import java.util.UUID;

public record AddStockRequest(
        UUID ingredientId,
        Double quantity) {
}
