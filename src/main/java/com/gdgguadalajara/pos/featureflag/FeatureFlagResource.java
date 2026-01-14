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
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("/api/feature-flags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FeatureFlagResource {

    @Inject
    CreateFeatureFlag createFeatureFlag;

    @Inject
    UpdateFeatureFlag updateFeatureFlag;

    @Inject
    DeleteFeatureFlag deleteFeatureFlag;

    @GET
    public List<FeatureFlag> list() {
        return FeatureFlag.listAll();
    }

    @GET
    @Path("/{key}")
    public FeatureFlag get(@PathParam("key") UUID key) {
        FeatureFlag flag = FeatureFlag.findById(key);
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
    @Path("/{key}")
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    @Transactional
    public FeatureFlag update(@PathParam("key") UUID key, UpdateFeatureFlagRequest request) {
        return updateFeatureFlag.execute(key, request);
    }

    @DELETE
    @Path("/{key}")
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    @Transactional
    public void delete(@PathParam("key") UUID key) {
        deleteFeatureFlag.execute(key);
    }
}
