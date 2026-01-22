package com.gdgguadalajara.pos.ticket;

import java.util.UUID;

import com.gdgguadalajara.pos.ticketItem.application.CreateTicketItem;
import com.gdgguadalajara.pos.ticketItem.application.DeleteTicketItem;
import com.gdgguadalajara.pos.ticketItem.model.TicketItem;
import com.gdgguadalajara.pos.ticketItem.model.dto.CreateTicketItemRequest;

import io.quarkus.security.Authenticated;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@Path("/api/tickets/{ticketId}/items")
@AllArgsConstructor
public class TicketTicketItemResource {

    private final CreateTicketItem createTicketItem;
    private final DeleteTicketItem deleteTicketItem;

    @POST
    @Path("/{productId}")
    @Authenticated
    public TicketItem create(UUID ticketId, UUID productId, @Valid CreateTicketItemRequest request) {
        return createTicketItem.run(ticketId, productId, request);
    }

    @DELETE
    @Path("/{ticketItemId}")
    @Authenticated
    public void delete(UUID ticketId, UUID ticketItemId) {
        deleteTicketItem.run(ticketId, ticketItemId);
    }
}
