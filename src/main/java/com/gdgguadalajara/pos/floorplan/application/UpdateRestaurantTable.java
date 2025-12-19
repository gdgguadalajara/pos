package com.gdgguadalajara.pos.floorplan.application;

import java.util.UUID;

import com.gdgguadalajara.pos.floorplan.model.Floor;
import com.gdgguadalajara.pos.floorplan.model.RestaurantTable;
import com.gdgguadalajara.pos.floorplan.model.dto.UpdateRestaurantTableRequest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UpdateRestaurantTable {

    public RestaurantTable run(UUID uuid, UpdateRestaurantTableRequest request) {
        var table = RestaurantTable.<RestaurantTable>findById(uuid);
        var floor = Floor.<Floor>findById(request.flooUuid());
        if (table == null)
            throw new NotFoundException("Mesa no encontrada");
        if (floor == null)
            throw new NotFoundException("Piso no encontrado");
        table.name = request.name();
        table.posX = request.posX();
        table.posY = request.posY();
        table.status = request.status();
        table.floor = floor;
        table.persistAndFlush();
        return table;
    }
}
