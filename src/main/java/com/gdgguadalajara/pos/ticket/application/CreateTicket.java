package com.gdgguadalajara.pos.ticket.application;

import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.ticket.model.Ticket;
import com.gdgguadalajara.pos.ticket.model.TicketStatus;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class CreateTicket {

    private final GetCurrentSession getCurrentSession;

    public Ticket run() {
        var ticket = new Ticket();
        ticket.owner = getCurrentSession.run().user;
        ticket.status = TicketStatus.OPEN;
        ticket.persist();
        return ticket;
    }

}
