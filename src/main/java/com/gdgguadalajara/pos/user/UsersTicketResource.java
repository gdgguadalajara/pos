package com.gdgguadalajara.pos.user;

import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.common.model.PaginatedResponse;
import com.gdgguadalajara.pos.common.util.PanacheCriteria;
import com.gdgguadalajara.pos.ticket.model.Ticket;

import io.quarkus.security.Authenticated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;

@Path("/api/users/me/tickets")
@AllArgsConstructor
public class UsersTicketResource {

    private final GetCurrentSession getCurrentSession;

    @GET
    @Authenticated
    public PaginatedResponse<Ticket> read(
            @QueryParam("page") @DefaultValue("1") @Positive @Valid Integer page,
            @QueryParam("size") @DefaultValue("10") @Positive @Max(100) @Valid Integer size) {
        return PanacheCriteria.of(Ticket.class)
                .eq("owner.id", getCurrentSession.run().user.id)
                .orderBy("createdAt DESC")
                .page(page, size)
                .getResult();
    }
}
