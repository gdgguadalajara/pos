package com.gdgguadalajara.pos.ticketItem.application;

import java.util.UUID;

import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.ticket.model.Ticket;
import com.gdgguadalajara.pos.ticketItem.model.TicketItem;
import com.gdgguadalajara.pos.ticketItem.model.TicketItemStatus;
import com.gdgguadalajara.pos.common.model.DomainException;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class DeleteTicketItem {

    private final GetCurrentSession getCurrentSession;

    public TicketItem run(UUID ticketId, UUID ticketItemId) {
        var account = getCurrentSession.run();
        var ticket = Ticket.<Ticket>findById(ticketId);
        if (ticket == null)
            throw DomainException.notFound("Ticket no encontrado");
        var ticketItem = TicketItem.<TicketItem>findById(ticketItemId);
        if (ticketItem == null)
            throw DomainException.notFound("TicketItem no encontrado");
        if (!ticketItem.status.equals(TicketItemStatus.ADDED))
            throw DomainException.badRequest("Solo es posible eliminar un ticketItem agregado");
        if (!ticketItem.ticket.id.equals(ticket.id))
            throw DomainException.badRequest("El ticketItem no pertenece al ticket");
        if (!ticket.owner.id.equals(account.user.id))
            throw DomainException.forbidden("No tienes permisos para modificar este ticket");
        if (!ticketItem.author.id.equals(account.user.id))
            throw DomainException.forbidden("No tienes permisos para eliminar este ticketItem");
        ticket.totalAmount = ticket.totalAmount.subtract(ticketItem.unitPrice);
        ticket.persistAndFlush();
        ticketItem.delete();
        return ticketItem;
    }
}
