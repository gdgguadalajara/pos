package com.gdgguadalajara.pos.inventory.model.dto;

import java.util.UUID;

public record CreateInventoryAuditRequest(
        UUID ingredientId,
        Double physicalStock) {
}
