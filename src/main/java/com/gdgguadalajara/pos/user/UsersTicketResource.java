package com.gdgguadalajara.pos.user;

import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.common.model.PaginatedResponse;
import com.gdgguadalajara.pos.common.util.PanacheCriteria;
import com.gdgguadalajara.pos.ticket.model.Ticket;
import com.gdgguadalajara.pos.ticket.model.dto.ReadTicketsFilter;

import io.quarkus.security.Authenticated;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@Path("/api/users/{userId}/tickets")
@AllArgsConstructor
public class UsersTicketResource {

    private final GetCurrentSession getCurrentSession;

    @GET
    @Authenticated
    public PaginatedResponse<Ticket> read(
            UUID userId,
            @BeanParam @Valid ReadTicketsFilter filter) {
        var session = getCurrentSession.run();
        if (!session.role.equals(AccountRole.ADMIN) && !session.user.id.equals(userId))
            throw DomainException.badRequest("No puedes ver tickets que no te pertenecen");
        var criteria = PanacheCriteria.of(Ticket.class)
                .eq("id", filter.id)
                .ge("totalAmount", filter.amount)
                .eq("status", filter.status)
                .orderBy("createdAt DESC")
                .page(filter.page, filter.size);
        if (session.role.equals(AccountRole.ADMIN))
            criteria.like("owner.name", filter.owner);
        else
            criteria.eq("owner.id", session.user.id);
        if (filter.createdAt != null)
            criteria.ge("createdAt", filter.createdAt.atStartOfDay())
                    .le("createdAt", filter.createdAt.plusDays(1).atStartOfDay());
        return criteria.getResult();
    }
}
