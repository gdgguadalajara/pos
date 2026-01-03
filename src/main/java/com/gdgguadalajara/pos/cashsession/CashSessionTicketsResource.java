package com.gdgguadalajara.pos.cashsession;

import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.cashsession.model.CashSession;
import com.gdgguadalajara.pos.common.PageBuilder;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.common.model.PaginatedResponse;
import com.gdgguadalajara.pos.expense.model.Expense;
import com.gdgguadalajara.pos.ticket.model.Ticket;
import com.gdgguadalajara.pos.ticket.model.TicketStatus;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;

@Path("/api/cash-sessions/{id}")
@AllArgsConstructor
public class CashSessionTicketsResource {

    @GET
    @Path("/tickets")
    @RolesAllowed({ AccountRole.ADMIN_ROLE })
    public PaginatedResponse<Ticket> read(UUID id,
            @QueryParam("page") @DefaultValue("1") @Positive @Valid Integer page,
            @QueryParam("size") @DefaultValue("10") @Positive @Max(100) @Valid Integer size) {
        var cashSession = CashSession.<CashSession>findById(id);
        if (cashSession == null)
            throw DomainException.notFound("La sesión de caja no existe");
        var query = Ticket.<Ticket>find("status = ?1 AND closedAt BETWEEN ?2 AND NOW()", TicketStatus.PAID,
                cashSession.openingDate);
        return PageBuilder.of(query, page, size);
    }

    @GET
    @Path("/expenses")
    @RolesAllowed({ AccountRole.ADMIN_ROLE })
    public PaginatedResponse<Expense> readExpenses(UUID id,
            @QueryParam("page") @DefaultValue("1") @Positive @Valid Integer page,
            @QueryParam("size") @DefaultValue("10") @Positive @Max(100) @Valid Integer size) {
        var cashSession = CashSession.<CashSession>findById(id);
        if (cashSession == null)
            throw DomainException.notFound("La sesión de caja no existe");
        var query = Expense.<Expense>find("createdAt BETWEEN ?1 AND NOW()",
                cashSession.openingDate);
        return PageBuilder.of(query, page, size);
    }
}
