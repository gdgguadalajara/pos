package com.gdgguadalajara.pos.invitation;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.common.PageBuilder;
import com.gdgguadalajara.pos.common.model.PaginatedResponse;
import com.gdgguadalajara.pos.invitation.application.CreateInvitation;
import com.gdgguadalajara.pos.invitation.model.Invitation;
import com.gdgguadalajara.pos.invitation.model.dto.CreateInvitationRequest;
import com.gdgguadalajara.pos.common.model.DomainException;

import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;

@Path("/api/invitations")
@AllArgsConstructor
public class InvitationResource {

    private final CreateInvitation createInvitation;

    @GET
    @RolesAllowed({ AccountRole.ADMIN_ROLE })
    public PaginatedResponse<Invitation> read(
            @QueryParam("page") @DefaultValue("1") @Positive @Valid Integer page,
            @QueryParam("size") @DefaultValue("10") @Positive @Max(100) @Valid Integer size) {
        var query = Invitation.<Invitation>findAll();
        return PageBuilder.of(query, page, size);
    }

    @GET
    @Path("/{token}")
    public Invitation readByToken(String token) {
        var invitation = Invitation.<Invitation>find("token", token).firstResult();
        if (invitation == null)
            throw DomainException.notFound("Invitación no encontrada");
        return invitation;
    }

    @POST
    @RolesAllowed({ AccountRole.ADMIN_ROLE })
    @Transactional
    public Invitation create(@Valid CreateInvitationRequest request) {
        return createInvitation.run(request);
    }

}
