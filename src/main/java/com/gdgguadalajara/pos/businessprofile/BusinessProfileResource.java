package com.gdgguadalajara.pos.businessprofile;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.businessprofile.application.UpdateBusinessProfile;
import com.gdgguadalajara.pos.businessprofile.model.BusinessProfile;
import com.gdgguadalajara.pos.businessprofile.model.dto.UpdateBusinessProfileRequest;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@Path("/api/businessprofile")
@AllArgsConstructor
public class BusinessProfileResource {

    private final UpdateBusinessProfile updateBusinessProfile;

    @GET
    @Authenticated
    public BusinessProfile read() {
        return BusinessProfile.findAll().firstResult();
    }

    @PUT
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public BusinessProfile update(@Valid UpdateBusinessProfileRequest request) {
        return updateBusinessProfile.run(request);
    }
}
