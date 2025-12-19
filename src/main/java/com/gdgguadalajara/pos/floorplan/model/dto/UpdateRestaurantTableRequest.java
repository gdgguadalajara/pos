package com.gdgguadalajara.pos.floorplan.model.dto;

import java.util.UUID;

import com.gdgguadalajara.pos.floorplan.model.RestaurantTableStatus;

public record UpdateRestaurantTableRequest(
                String name,
                Integer posX,
                Integer posY,
                RestaurantTableStatus status,
                UUID flooUuid) {
}
