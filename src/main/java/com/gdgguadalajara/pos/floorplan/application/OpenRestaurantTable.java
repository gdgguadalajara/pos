package com.gdgguadalajara.pos.floorplan.application;

import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.floorplan.model.RestaurantTable;
import com.gdgguadalajara.pos.floorplan.model.RestaurantTableStatus;
import com.gdgguadalajara.pos.ticket.application.CreateTicket;
import com.gdgguadalajara.pos.ticket.model.TicketServiceType;
import com.gdgguadalajara.pos.ticket.model.dto.CreateTicketRequest;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class OpenRestaurantTable {

    private final CreateTicket createTicket;
    private final ObjectMapper objectMapper;

    public RestaurantTable run(UUID uuid, OpenRestauranTableRequest request) {
        var restaurantTable = RestaurantTable.<RestaurantTable>findById(uuid);
        if (!restaurantTable.status.equals(RestaurantTableStatus.AVAILABLE))
            throw DomainException.badRequest("La mesa no está disponible");
        var ticket = createTicket.run(new CreateTicketRequest(TicketServiceType.DINE_IN, request.dinerCount()));
        restaurantTable.status = RestaurantTableStatus.OCCUPIED;
        restaurantTable.ticket = ticket;
        restaurantTable.persistAndFlush();
        ticket.tableSnapshot = objectMapper.convertValue(restaurantTable,
                new TypeReference<Map<String, Object>>() {
                });
        ticket.persistAndFlush();
        return restaurantTable;
    }

}
