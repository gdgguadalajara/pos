package com.gdgguadalajara.pos.floorplan.application;

import java.util.UUID;

import com.gdgguadalajara.pos.floorplan.model.Floor;
import com.gdgguadalajara.pos.floorplan.model.RestaurantTable;
import com.gdgguadalajara.pos.floorplan.model.dto.UpdateRestaurantTableRequest;
import com.gdgguadalajara.pos.common.model.DomainException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class UpdateRestaurantTable {

    public RestaurantTable run(UUID uuid, UpdateRestaurantTableRequest request) {
        var table = RestaurantTable.<RestaurantTable>findById(uuid);
        var floor = Floor.<Floor>findById(request.flooUuid());
        if (table == null)
            throw DomainException.notFound("Mesa no encontrada");
        if (floor == null)
            throw DomainException.notFound("Piso no encontrado");
        table.name = request.name();
        table.posX = request.posX();
        table.posY = request.posY();
        table.status = request.status();
        table.floor = floor;
        table.persistAndFlush();
        return table;
    }
}
