package com.gdgguadalajara.pos.productioncenter.model.dto;

import java.util.UUID;

import com.gdgguadalajara.pos.ticket.model.TicketServiceType;
import com.gdgguadalajara.pos.ticketItem.model.TicketItem;

public record ProductionCenterTicketItemsResponse(
        UUID ticketId,
        UUID tableId,
        String tableName,
        TicketServiceType serviceType,
        TicketItem item) {
}
