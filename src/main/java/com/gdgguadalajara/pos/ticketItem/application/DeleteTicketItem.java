package com.gdgguadalajara.pos.ticketItem.application;

import java.util.UUID;

import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.ticket.model.Ticket;
import com.gdgguadalajara.pos.ticketItem.model.TicketItem;
import com.gdgguadalajara.pos.ticketItem.model.TicketItemStatus;

import io.quarkus.security.ForbiddenException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class DeleteTicketItem {

    private final GetCurrentSession getCurrentSession;

    public TicketItem run(UUID ticketId, UUID ticketItemId) {
        var account = getCurrentSession.run();
        var ticket = Ticket.<Ticket>findById(ticketId);
        if (ticket == null)
            throw new NotFoundException("Ticket no encontrado");
        var ticketItem = TicketItem.<TicketItem>findById(ticketItemId);
        if (ticketItem == null)
            throw new NotFoundException("TicketItem no encontrado");
        if (!ticketItem.status.equals(TicketItemStatus.ADDED))
            throw new ForbiddenException("Solo es posible eliminar un ticketItem agregado");
        if (!ticketItem.ticket.id.equals(ticket.id))
            throw new ForbiddenException("El ticketItem no pertenece al ticket");
        if (!ticket.owner.id.equals(account.user.id))
            throw new ForbiddenException("No tienes permisos para modificar este ticket");
        if (!ticketItem.author.id.equals(account.user.id))
            throw new ForbiddenException("No tienes permisos para eliminar este ticketItem");
        ticket.totalAmount = ticket.totalAmount.subtract(ticketItem.unitPrice);
        ticket.persistAndFlush();
        ticketItem.delete();
        return ticketItem;
    }
}
