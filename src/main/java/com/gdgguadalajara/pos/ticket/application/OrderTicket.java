package com.gdgguadalajara.pos.ticket.application;

import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.ticket.model.Ticket;
import com.gdgguadalajara.pos.ticket.model.TicketStatus;
import com.gdgguadalajara.pos.ticketItem.model.TicketItemStatus;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.productioncenter.application.GetEffectiveProductionCenter;
import com.gdgguadalajara.pos.productioncenter.application.ServerSideProductionCenterEvents;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class OrderTicket {

    private final GetCurrentSession getCurrentSession;
    private final GetEffectiveProductionCenter getEffectiveProductionCenter;
    private final ServerSideProductionCenterEvents serverSideProductionCenterEvents;

    public Ticket run(UUID uuid) {
        var session = getCurrentSession.run();
        var ticket = Ticket.<Ticket>findById(uuid);
        if (ticket == null)
            throw DomainException.notFound("Ticket no encontrado");
        if (session.role.equals(AccountRole.WAITER) && !ticket.owner.id.equals(session.user.id))
            throw DomainException.forbidden("No puedes modificar este ticket");
        if (!ticket.status.equals(TicketStatus.OPEN))
            throw DomainException.badRequest("No puedes modificar este ticket");
        ticket.items.stream()
                .filter(item -> item.status.equals(TicketItemStatus.ADDED))
                .forEach(item -> {
                    item.status = TicketItemStatus.ORDERED;
                    item.persistAndFlush();
                    serverSideProductionCenterEvents.publish(getEffectiveProductionCenter.run(item.originalProductId));
                });
        return ticket;
    }
}
