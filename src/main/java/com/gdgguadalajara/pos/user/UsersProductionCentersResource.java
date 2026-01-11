package com.gdgguadalajara.pos.user;

import java.util.Set;
import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.productioncenter.model.ProductionCenter;
import com.gdgguadalajara.pos.user.application.CreateUsertProductionCenter;
import com.gdgguadalajara.pos.user.application.DeleteUsertProductionCenter;
import com.gdgguadalajara.pos.user.model.User;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@Path("/api/users/{userId}/production-centers")
@AllArgsConstructor
public class UsersProductionCentersResource {

    private final GetCurrentSession getCurrentSession;
    private final CreateUsertProductionCenter createUsertProductionCenter;
    private final DeleteUsertProductionCenter deleteUsertProductionCenter;

    @GET
    @Authenticated
    public Set<ProductionCenter> read(UUID userId) {
        var session = getCurrentSession.run();
        if (!session.role.equals(AccountRole.ADMIN) && !session.user.id.equals(userId))
            return Set.of();
        var user = User.<User>findById(userId);
        if (user == null)
            throw DomainException.notFound("Usuario no encontrado");
        return user.account.productionCenters;
    }

    @POST
    @Path("/{productionCenterId}")
    @Transactional
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public Set<ProductionCenter> create(UUID userId, UUID productionCenterId) {
        return createUsertProductionCenter.run(userId, productionCenterId);
    }

    @DELETE
    @Path("/{productionCenterId}")
    @Transactional
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public void delete(UUID userId, UUID productionCenterId) {
        deleteUsertProductionCenter.run(userId, productionCenterId);
    }
}
