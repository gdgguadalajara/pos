package com.gdgguadalajara.pos.ticket;

import java.util.UUID;

import com.gdgguadalajara.pos.ticket.application.CreateTicket;
import com.gdgguadalajara.pos.ticket.application.DeleteTicket;
import com.gdgguadalajara.pos.ticket.application.OrderTicket;
import com.gdgguadalajara.pos.ticket.model.Ticket;
import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.common.PageBuilder;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.common.model.PaginatedResponse;

import io.quarkus.panache.common.Sort;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;

@Path("/api/tickets")
@AllArgsConstructor
public class TicketResource {

    private final CreateTicket createTicket;
    private final DeleteTicket deleteTicket;
    private final OrderTicket orderTicket;

    @POST
    @Transactional
    @Authenticated
    public Ticket create() {
        return createTicket.run();
    }

    @GET
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public PaginatedResponse<Ticket> read(
            @QueryParam("page") @DefaultValue("1") @Positive @Valid Integer page,
            @QueryParam("size") @DefaultValue("10") @Positive @Max(100) @Valid Integer size) {
        return PageBuilder.of(Ticket.findAll(Sort.by("createdAt")), page, size);
    }

    @GET
    @Path("/{id}")
    @Authenticated
    public Ticket readById(UUID id) {
        var ticket = Ticket.<Ticket>findById(id);
        if (ticket == null)
            throw DomainException.notFound("Ticket no encontrado");
        return ticket;
    }

    @PUT
    @Path("/{id}/order")
    @Transactional
    @Authenticated
    public Ticket order(UUID id) {
        return orderTicket.run(id);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Authenticated
    public void delete(UUID id) {
        deleteTicket.run(id);
    }
}
