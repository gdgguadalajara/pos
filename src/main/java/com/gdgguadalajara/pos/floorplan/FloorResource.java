package com.gdgguadalajara.pos.floorplan;

import java.util.List;
import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.floorplan.application.CreateFloor;
import com.gdgguadalajara.pos.floorplan.application.CreateRestaurantTable;
import com.gdgguadalajara.pos.floorplan.application.DeleteFloor;
import com.gdgguadalajara.pos.floorplan.model.Floor;
import com.gdgguadalajara.pos.floorplan.model.RestaurantTable;
import com.gdgguadalajara.pos.floorplan.model.dto.CreateFloorRequest;
import com.gdgguadalajara.pos.floorplan.model.dto.CreateRestaurantTableRequest;

import io.quarkus.panache.common.Sort;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
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
    private final CreateRestaurantTable createRestaurantTable;

    @POST
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public Floor create(@Valid CreateFloorRequest request) {
        return createFloor.run(request);
    }

    @POST
    @Path("/{floorId}/tables")
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public RestaurantTable create(UUID floorId, @Valid CreateRestaurantTableRequest request) {
        return createRestaurantTable.run(floorId, request);
    }

    @GET
    @Path("/{floorId}/tables")
    @Authenticated
    public List<RestaurantTable> readRestaurantTablesByFloor(UUID floorId) {
        return RestaurantTable.<RestaurantTable>find("floor.id", floorId).list();
    }

    @GET
    @Authenticated
    public List<Floor> read() {
        return Floor.<Floor>listAll(Sort.by("levelOrder"));
    }

    @DELETE
    @Path("/{uuid}")
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public void delete(UUID uuid) {
        try {
            deleteFloor.run(Floor.findById(uuid));
        } catch (Exception e) {
        }
    }
}
