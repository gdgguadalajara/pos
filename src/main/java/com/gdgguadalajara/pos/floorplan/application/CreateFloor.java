package com.gdgguadalajara.pos.floorplan.application;

import com.gdgguadalajara.pos.floorplan.model.Floor;
import com.gdgguadalajara.pos.floorplan.model.dto.CreateFloorRequest;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateFloor {
    
    public Floor run(CreateFloorRequest request) {
        var floor = new Floor();
        floor.name = request.name();
        floor.levelOrder = request.levelOrder();
        floor.gridWidth = request.gridWidth();
        floor.gridHeight = request.gridHeight();
        floor.persistAndFlush();
        return floor;
    }
}
