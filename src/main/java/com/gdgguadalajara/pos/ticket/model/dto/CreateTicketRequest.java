package com.gdgguadalajara.pos.ticket.model.dto;

import com.gdgguadalajara.pos.ticket.model.TicketServiceType;

import io.smallrye.common.constraint.NotNull;

public record CreateTicketRequest(
                @NotNull TicketServiceType serviceType) {
}
