package com.gdgguadalajara.pos.floorplan.application;

import java.util.UUID;

import com.gdgguadalajara.pos.floorplan.model.Floor;
import com.gdgguadalajara.pos.floorplan.model.RestaurantTable;
import com.gdgguadalajara.pos.floorplan.model.RestaurantTableStatus;
import com.gdgguadalajara.pos.floorplan.model.dto.CreateRestaurantTableRequest;
import com.gdgguadalajara.pos.common.model.DomainException;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateRestaurantTable {

    public RestaurantTable run(UUID floorId, CreateRestaurantTableRequest request) {
        var floor = Floor.<Floor>findById(floorId);
        if (floor == null)
            throw DomainException.notFound("Piso no encontrado");
        var table = new RestaurantTable();
        table.floor = floor;
        table.name = request.name();
        table.posX = request.posX();
        table.posY = request.posY();
        table.status = RestaurantTableStatus.AVAILABLE;
        table.persistAndFlush();
        return table;
    }
}
