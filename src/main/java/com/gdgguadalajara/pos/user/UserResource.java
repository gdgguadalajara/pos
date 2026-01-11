package com.gdgguadalajara.pos.user;

import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.common.model.PaginatedResponse;
import com.gdgguadalajara.pos.common.util.PanacheCriteria;
import com.gdgguadalajara.pos.user.model.User;
import com.gdgguadalajara.pos.user.model.dto.ReadUsersFilter;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api/users")
public class UserResource {

    @GET
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public PaginatedResponse<User> getAll(@BeanParam @Valid ReadUsersFilter filter) {
        return PanacheCriteria.of(User.class)
                .eq("id", filter.id)
                .like("name", filter.name)
                .like("email", filter.email)
                .like("account.username", filter.username)
                .eq("account.status", filter.status)
                .eq("account.role", filter.role)
                .orderBy(filter.sort)
                .page(filter.page, filter.size)
                .getResult();
    }

    @GET
    @Path("/{uuid}")
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public User read(UUID uuid) {
        var user = User.<User>findById(uuid);
        if (user == null)
            throw DomainException.notFound("Usuario no encontrado");
        return user;
    }
}
