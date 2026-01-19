package com.gdgguadalajara.pos.ticket.model.dto;

import com.gdgguadalajara.pos.ticket.model.TicketServiceType;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateTicketRequest(
                @NotNull TicketServiceType serviceType,
                @Positive
                @NotNull Integer dinerCount
            ) {
}
