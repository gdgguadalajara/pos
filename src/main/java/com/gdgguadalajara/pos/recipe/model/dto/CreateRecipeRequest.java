package com.gdgguadalajara.pos.recipe.model.dto;

import java.util.List;
import java.util.UUID;

public record CreateRecipeRequest(
        String name,
        String description,
        UUID productId,
        List<UUID> ingredientIds) {
}
