package com.gdgguadalajara.pos.floorplan.application;

import java.util.UUID;

import com.gdgguadalajara.pos.floorplan.model.Floor;
import com.gdgguadalajara.pos.floorplan.model.dto.UpdateFloorRequest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UpdateFloor {

    public Floor run(UUID uuid, UpdateFloorRequest request) {
        var floor = Floor.<Floor>findById(uuid);
        if (floor == null)
            throw new NotFoundException("Piso no encontrado");
        floor.name = request.name();
        floor.levelOrder = request.levelOrder();
        floor.gridWidth = request.gridWidth();
        floor.gridHeight = request.gridHeight();
        floor.persistAndFlush();
        return floor;
    }
}
