package com.gdgguadalajara.pos.ticket.application;

import java.time.LocalDateTime;

import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.floorplan.model.RestaurantTableStatus;
import com.gdgguadalajara.pos.ticket.model.Ticket;
import com.gdgguadalajara.pos.ticket.model.TicketStatus;
import com.gdgguadalajara.pos.ticketItem.model.TicketItemStatus;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class PayTicket {

    public Ticket run(Ticket ticket) {
        if (!ticket.status.equals(TicketStatus.OPEN))
            throw DomainException.badRequest("No puedes modificar este ticket");
        ticket.items.stream()
                .filter(item -> item.status.equals(TicketItemStatus.ADDED)
                        || item.status.equals(TicketItemStatus.ORDERED))
                .forEach(item -> {
                    item.status = TicketItemStatus.PAID;
                    item.persistAndFlush();
                });
        ticket.status = TicketStatus.PAID;
        ticket.closedAt = LocalDateTime.now();
        var table = ticket.table;
        if (table != null) {
            table.status = RestaurantTableStatus.AVAILABLE;
            table.ticket = null;
            table.persistAndFlush();
        }
        ticket.table = null;
        ticket.persistAndFlush();
        return ticket;
    }

}
