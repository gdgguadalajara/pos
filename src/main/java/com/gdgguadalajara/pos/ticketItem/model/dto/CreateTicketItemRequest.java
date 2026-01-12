package com.gdgguadalajara.pos.ticketItem.model.dto;

import jakarta.validation.constraints.NotNull;

public record CreateTicketItemRequest(
                @NotNull Boolean isTakeAway) {
}
