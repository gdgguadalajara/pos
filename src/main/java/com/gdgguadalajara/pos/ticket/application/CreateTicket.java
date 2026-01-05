package com.gdgguadalajara.pos.ticket.application;

import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.cashsession.application.GetCurrentCashSession;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.ticket.model.Ticket;
import com.gdgguadalajara.pos.ticket.model.TicketStatus;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class CreateTicket {

    private final GetCurrentSession getCurrentSession;
    private final GetCurrentCashSession getCurrentCashSession;

    public Ticket run() {
        var currentCashSession = getCurrentCashSession.run();
        if (currentCashSession == null)
            throw DomainException.forbidden("No hay una sesión de caja abierta");
        var ticket = new Ticket();
        ticket.owner = getCurrentSession.run().user;
        ticket.status = TicketStatus.OPEN;
        ticket.persist();
        return ticket;
    }

}
