package com.gdgguadalajara.pos.user;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.common.PageBuilder;
import com.gdgguadalajara.pos.common.model.PaginatedResponse;
import com.gdgguadalajara.pos.user.model.User;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/api/users")
public class UserResource {

    @GET
    @RolesAllowed({ AccountRole.ADMIN_ROLE })
    public PaginatedResponse<User> getAll(
            @QueryParam("page") @DefaultValue("1") @Positive @Valid Integer page,
            @QueryParam("size") @DefaultValue("10") @Positive @Max(100) @Valid Integer size) {
        return PageBuilder.of(User.findAll(), page, size);
    }
}
