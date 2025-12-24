package com.gdgguadalajara.pos.floorplan;

import java.util.List;
import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.floorplan.application.CreateRestaurantTable;
import com.gdgguadalajara.pos.floorplan.application.DeleteRestauranTable;
import com.gdgguadalajara.pos.floorplan.application.UpdateRestaurantTable;
import com.gdgguadalajara.pos.floorplan.model.RestaurantTable;
import com.gdgguadalajara.pos.floorplan.model.dto.CreateRestaurantTableRequest;
import com.gdgguadalajara.pos.floorplan.model.dto.UpdateRestaurantTableRequest;

import io.quarkus.panache.common.Sort;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@Path("/api/tables")
@AllArgsConstructor
public class RestaurantTableResource {

    private final CreateRestaurantTable createRestaurantTable;
    private final UpdateRestaurantTable updateRestaurantTable;
    private final DeleteRestauranTable deleteRestauranTable;

    @POST
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    @Transactional
    public RestaurantTable create(CreateRestaurantTableRequest request) {
        return createRestaurantTable.run(request);
    }

    @GET
    @Authenticated
    public List<RestaurantTable> read() {
        return RestaurantTable.listAll(Sort.by("createdAt"));
    }

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
