package com.gdgguadalajara.pos.ticket.application;

import java.time.LocalDateTime;

import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.floorplan.model.RestaurantTableStatus;
import com.gdgguadalajara.pos.ticket.model.Ticket;
import com.gdgguadalajara.pos.ticket.model.TicketStatus;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
@Transactional
public class PayTicket {

    private final OrderTicket orderTicket;

    public Ticket run(Ticket ticket) {
        if (!ticket.status.equals(TicketStatus.OPEN))
            throw DomainException.badRequest("No puedes modificar este ticket");
        orderTicket.run(ticket.id);
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
