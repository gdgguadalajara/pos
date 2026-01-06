package com.gdgguadalajara.pos.ticket.application;

import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.ticket.model.Ticket;
import com.gdgguadalajara.pos.ticket.model.TicketStatus;
import com.gdgguadalajara.pos.ticket.model.dto.CreateTicketRequest;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class CreateTicket {

    private final GetCurrentSession getCurrentSession;

    public Ticket run(CreateTicketRequest request) {
        var ticket = new Ticket();
        ticket.owner = getCurrentSession.run().user;
        ticket.status = TicketStatus.OPEN;
        ticket.serviceType = request.serviceType();
        ticket.persist();
        return ticket;
    }

}
