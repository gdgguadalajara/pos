package com.gdgguadalajara.pos.floorplan.application;

import com.gdgguadalajara.pos.floorplan.model.RestaurantTable;
import com.gdgguadalajara.pos.floorplan.model.RestaurantTableStatus;
import com.gdgguadalajara.pos.floorplan.model.dto.CreateRestaurantTableRequest;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateRestaurantTable {

    public RestaurantTable run(CreateRestaurantTableRequest request) {
        var table = new RestaurantTable();
        table.name = request.name();
        table.posX = request.posX();
        table.posY = request.posY();
        table.status = RestaurantTableStatus.AVAILABLE;
        table.persistAndFlush();
        return table;
    }
}
