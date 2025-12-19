package com.gdgguadalajara.pos.floorplan.application;

import com.gdgguadalajara.pos.floorplan.model.Floor;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeleteFloor {
    
    public void run(Floor floor) {
        floor.delete();
    }
}
