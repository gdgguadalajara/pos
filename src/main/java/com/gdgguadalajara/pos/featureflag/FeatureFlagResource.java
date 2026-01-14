package com.gdgguadalajara.pos.featureflag;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.featureflag.application.CreateFeatureFlag;
import com.gdgguadalajara.pos.featureflag.application.DeleteFeatureFlag;
import com.gdgguadalajara.pos.featureflag.application.UpdateFeatureFlag;
import com.gdgguadalajara.pos.featureflag.model.FeatureFlag;
import com.gdgguadalajara.pos.featureflag.model.dto.CreateFeatureFlagRequest;
import com.gdgguadalajara.pos.featureflag.model.dto.UpdateFeatureFlagRequest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@Path("/api/feature-flags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class FeatureFlagResource {

    private final CreateFeatureFlag createFeatureFlag;
    private final UpdateFeatureFlag updateFeatureFlag;
    private final DeleteFeatureFlag deleteFeatureFlag;

    @GET
    public List<FeatureFlag> list() {
        return FeatureFlag.listAll();
    }

    @GET
    @Path("/{uuid}")
    public FeatureFlag get(@PathParam("uuid") UUID uuid) {
        FeatureFlag flag = FeatureFlag.findById(uuid);
        if (flag == null)
            throw DomainException.notFound("Feature flag no encontrada");
        return flag;
    }

    @POST
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    @Transactional
    public FeatureFlag create(CreateFeatureFlagRequest request) {
        return createFeatureFlag.execute(request);
    }

    @PUT
    @Path("/{uuid}")
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    @Transactional
    public FeatureFlag update(@PathParam("uuid") UUID uuid, UpdateFeatureFlagRequest request) {
        return updateFeatureFlag.execute(uuid, request);
    }

    @DELETE
    @Path("/{uuid}")
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    @Transactional
    public void delete(@PathParam("uuid") UUID uuid) {
        deleteFeatureFlag.execute(uuid);
    }
}
