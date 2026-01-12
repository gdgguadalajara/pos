package com.gdgguadalajara.pos.ticketItem;

import java.util.UUID;

import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.ticketItem.model.TicketItem;
import com.gdgguadalajara.pos.ticketItem.model.TicketItemStatus;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/api/ticket-item")
public class TicketItemResource {

    @PUT
    @Path("/{uuid}/preparing")
    @Transactional
    public Boolean preparing(UUID uuid) {
        var item = TicketItem.<TicketItem>findById(uuid);
        if (item == null)
            throw DomainException.notFound("TicketItem no encontrado");
        if (item.status != TicketItemStatus.ORDERED)
            throw DomainException.forbidden("El estado del TicketItem debe ser ORDERED para cambiar a PREPARING");
        item.status = TicketItemStatus.PREPARING;
        item.persistAndFlush();
        return Boolean.TRUE;
    }

    @PUT
    @Path("/{uuid}/ready")
    @Transactional
    public Boolean ready(UUID uuid) {
        var item = TicketItem.<TicketItem>findById(uuid);
        if (item == null)
            throw DomainException.notFound("TicketItem no encontrado");
        if (item.status != TicketItemStatus.PREPARING)
            throw DomainException.forbidden("El estado del TicketItem debe ser PREPARING para cambiar a READY");
        item.status = TicketItemStatus.READY;
        item.persistAndFlush();
        return Boolean.TRUE;
    }
}
