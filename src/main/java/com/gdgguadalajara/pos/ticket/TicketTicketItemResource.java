package com.gdgguadalajara.pos.ticket;

import java.util.UUID;

import com.gdgguadalajara.pos.ticketItem.application.CreateTicketItem;
import com.gdgguadalajara.pos.ticketItem.model.TicketItem;

import io.quarkus.security.Authenticated;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@Path("/api/tickets/{ticketId}/items")
@AllArgsConstructor
public class TicketTicketItemResource {

    private final CreateTicketItem createTicketItem;

    @POST
    @Path("/{productId}")
    @Authenticated
    @Transactional
    public TicketItem create(UUID ticketId, UUID productId) {
        return createTicketItem.run(ticketId, productId);
    }

}
