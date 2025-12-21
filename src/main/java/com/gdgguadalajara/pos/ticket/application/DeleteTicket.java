package com.gdgguadalajara.pos.ticket.application;

import java.util.UUID;

import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.ticket.model.Ticket;

import io.quarkus.security.ForbiddenException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class DeleteTicket {

    private final GetCurrentSession getCurrentSession;

    public void run(UUID tikcetId) {
        var ticket = Ticket.<Ticket>findById(tikcetId);
        if (ticket == null)
            return;
        var session = getCurrentSession.run();
        if (!ticket.owner.id.equals(session.id))
            throw new ForbiddenException("No puedes modificar este ticket");
        if (ticket.items.size() > 0)
            throw new ForbiddenException("No puedes eliminar un ticket con items");
        ticket.delete();
    }
}
