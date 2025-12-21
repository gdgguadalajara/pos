package com.gdgguadalajara.pos.ticket;

import java.util.UUID;

import com.gdgguadalajara.pos.ticket.application.CreateTicket;
import com.gdgguadalajara.pos.ticket.application.DeleteTicket;
import com.gdgguadalajara.pos.ticket.model.Ticket;

import io.quarkus.security.Authenticated;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@Path("/api/tickets")
@AllArgsConstructor
public class TicketResource {

    private final CreateTicket createTicket;
    private final DeleteTicket deleteTicket;

    @POST
    @Transactional
    @Authenticated
    public Ticket create() {
        return createTicket.run();
    }

    @GET
    @Path("/{id}")
    @Authenticated
    public Ticket readById(UUID id) {
        var ticket = Ticket.<Ticket>findById(id);
        if (ticket == null)
            throw new NotFoundException("Ticket no encontrado");
        return ticket;
    }

    @DELETE
    @Path("/{id}")
    @Authenticated
    public void delete(UUID id) {
        deleteTicket.run(id);
    }
}
