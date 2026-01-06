package com.gdgguadalajara.pos.ticket;

import java.util.List;
import java.util.UUID;

import com.gdgguadalajara.pos.ticket.application.CreateTicket;
import com.gdgguadalajara.pos.ticket.application.DeleteTicket;
import com.gdgguadalajara.pos.ticket.application.OrderTicket;
import com.gdgguadalajara.pos.ticket.model.Ticket;
import com.gdgguadalajara.pos.ticket.model.dto.CreateTicketRequest;
import com.gdgguadalajara.pos.ticket.model.dto.ReadTicketsFilter;
import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.common.model.PaginatedResponse;
import com.gdgguadalajara.pos.common.util.PanacheCriteria;
import com.gdgguadalajara.pos.payment.model.Payment;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
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
    public Ticket create(@Valid CreateTicketRequest request) {
        return createTicket.run(request);
    }

    @GET
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public PaginatedResponse<Ticket> read(@BeanParam @Valid ReadTicketsFilter filter) {
        var criteria = PanacheCriteria.of(Ticket.class)
                .eq("id", filter.id)
                .like("owner.name", filter.owner)
                .ge("totalAmount", filter.amount)
                .eq("status", filter.status)
                .orderBy("createdAt DESC")
                .page(filter.page, filter.size);
        if (filter.createdAt != null)
            criteria.ge("createdAt", filter.createdAt.atStartOfDay())
                    .le("createdAt", filter.createdAt.plusDays(1).atStartOfDay());
        return criteria.getResult();
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

    @GET
    @Authenticated
    @Path("/{id}/payments")
    public List<Payment> read(UUID id) {
        return Payment.<Payment>find("ticket.id", id).list();
    }
}
