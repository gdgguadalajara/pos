package com.gdgguadalajara.pos.ticketItem.application;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.product.model.Product;
import com.gdgguadalajara.pos.ticket.model.Ticket;
import com.gdgguadalajara.pos.ticketItem.model.TicketItem;
import com.gdgguadalajara.pos.ticketItem.model.TicketItemStatus;
import com.gdgguadalajara.pos.common.model.DomainException;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class CreateTicketItem {

    private final GetCurrentSession getCurrentSession;
    private final ObjectMapper objectMapper;

    public TicketItem run(UUID ticketId, UUID productId) {
        var account = getCurrentSession.run();
        var product = Product.<Product>findById(productId);
        if (product == null)
            throw DomainException.notFound("Producto no encontrado");
        if (!product.isEnabled)
            throw DomainException.badRequest("No se puede agregar un producto inactivo al ticket");
        var localDateNow = LocalDate.now();
        var localTimeNow = LocalTime.now();
        if (product.availableFrom.isAfter(localDateNow) || product.availableUntil.isBefore(localDateNow))
            throw DomainException.badRequest("El producto no está disponible en este momento");
        if (localTimeNow.isBefore(product.availableFromTime) || localTimeNow.isAfter(product.availableUntilTime))
            throw DomainException.badRequest("El producto no está disponible en este momento del día");
        if (!product.availableDays.contains(localDateNow.getDayOfWeek()))
            throw DomainException.badRequest("El producto no está disponible en este día de la semana");
        var ticket = Ticket.<Ticket>findById(ticketId);
        if (ticket == null)
            throw DomainException.notFound("Ticket no encontrado");
        if (!ticket.owner.id.equals(account.user.id))
            throw DomainException.forbidden("No tienes permisos para modificar este ticket");
        var ticketItem = new TicketItem();
        ticketItem.ticket = ticket;
        ticketItem.author = account.user;
        ticketItem.originalProductId = product.id;
        ticketItem.productName = product.name;
        ticketItem.unitPrice = product.price;
        ticketItem.status = TicketItemStatus.ADDED;
        ticketItem.productSnapshot = objectMapper.convertValue(product, new TypeReference<Map<String, Object>>() {
        });
        ticketItem.persistAndFlush();
        ticket.totalAmount = ticket.totalAmount.add(ticketItem.unitPrice);
        ticket.persistAndFlush();
        return ticketItem;
    }
}
