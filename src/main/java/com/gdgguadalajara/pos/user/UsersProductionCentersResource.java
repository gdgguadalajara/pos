package com.gdgguadalajara.pos.user;

import java.util.Set;
import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.productioncenter.model.ProductionCenter;

import io.quarkus.security.Authenticated;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@Path("/api/users/{userId}/production-centers")
@AllArgsConstructor
public class UsersProductionCentersResource {

    private final GetCurrentSession getCurrentSession;

    @GET
    @Authenticated
    public Set<ProductionCenter> read(UUID userId) {
        var session = getCurrentSession.run();
        if (!session.role.equals(AccountRole.ADMIN) && !session.user.id.equals(userId))
            return Set.of();
        return session.productionCenters;
    }

}
