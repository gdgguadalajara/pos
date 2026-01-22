package com.gdgguadalajara.pos.ticket.application;

import java.util.UUID;

import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.ticket.model.Ticket;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.floorplan.model.RestaurantTableStatus;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
@Transactional
public class DeleteTicket {

    private final GetCurrentSession getCurrentSession;

    public void run(UUID tikcetId) {
        var ticket = Ticket.<Ticket>findById(tikcetId);
        if (ticket == null)
            return;
        var session = getCurrentSession.run();
        if (!ticket.owner.id.equals(session.user.id))
            throw DomainException.forbidden("No puedes modificar este ticket");
        if (ticket.items.size() > 0)
            throw DomainException.badRequest("No puedes eliminar un ticket con items");
        var table = ticket.table;
        if (table != null) {
            table.status = RestaurantTableStatus.AVAILABLE;
            table.ticket = null;
            table.persistAndFlush();
        }
        ticket.delete();
    }
}
