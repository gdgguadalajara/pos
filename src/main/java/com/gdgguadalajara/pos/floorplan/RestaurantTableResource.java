package com.gdgguadalajara.pos.floorplan;

import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.floorplan.application.DeleteRestauranTable;
import com.gdgguadalajara.pos.floorplan.application.UpdateRestaurantTable;
import com.gdgguadalajara.pos.floorplan.model.RestaurantTable;
import com.gdgguadalajara.pos.floorplan.model.dto.UpdateRestaurantTableRequest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@Path("/api/tables")
@AllArgsConstructor
public class RestaurantTableResource {

    private final UpdateRestaurantTable updateRestaurantTable;
    private final DeleteRestauranTable deleteRestauranTable;

    @PUT
    @Path("/{uuid}")
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    @Transactional
    public RestaurantTable update(UUID uuid, UpdateRestaurantTableRequest request) {
        return updateRestaurantTable.run(uuid, request);
    }

    @Path("/{uuid}")
    @DELETE
    @Transactional
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public void delete(UUID uuid) {
        try {
            deleteRestauranTable.run(RestaurantTable.findById(uuid));
        } catch (Exception e) {
        }
    }
}
