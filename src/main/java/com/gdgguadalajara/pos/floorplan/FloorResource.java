package com.gdgguadalajara.pos.floorplan;

import java.util.List;
import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.floorplan.application.CreateFloor;
import com.gdgguadalajara.pos.floorplan.application.DeleteFloor;
import com.gdgguadalajara.pos.floorplan.model.Floor;
import com.gdgguadalajara.pos.floorplan.model.dto.CreateFloorRequest;

import io.quarkus.panache.common.Sort;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@Path("/api/floors")
@AllArgsConstructor
public class FloorResource {

    private final CreateFloor createFloor;
    private final DeleteFloor deleteFloor;

    @POST
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    @Transactional
    public Floor create(CreateFloorRequest request) {
        return createFloor.run(request);
    }

    @GET
    @Authenticated
    public List<Floor> read() {
        return Floor.<Floor>listAll(Sort.by("levelOrder"));
    }

    @DELETE
    @Path("/{uuid}")
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    @Transactional
    public void delete(UUID uuid) {
        try {
            deleteFloor.run(Floor.findById(uuid));
        } catch (Exception e) {
        }
    }
}
