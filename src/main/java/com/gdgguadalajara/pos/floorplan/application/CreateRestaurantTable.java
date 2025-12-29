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
        var tableVerify = RestaurantTable
                .<RestaurantTable>find("floor.id = ?1 AND posX = ?2 AND posY = ?3", floorId, request.posX(),
                        request.posY())
                .firstResult();
        if (tableVerify != null)
            throw DomainException.badRequest("Ya existe una mesa en la posición indicada");
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
