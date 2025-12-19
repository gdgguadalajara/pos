package com.gdgguadalajara.pos.floorplan.application;

import com.gdgguadalajara.pos.floorplan.model.RestaurantTable;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeleteRestauranTable {

    public void run(RestaurantTable restaurantTable) {
        restaurantTable.delete();
    }
}
