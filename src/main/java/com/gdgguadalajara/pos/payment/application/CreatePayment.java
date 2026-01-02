package com.gdgguadalajara.pos.payment.application;

import java.math.BigDecimal;
import java.util.UUID;

import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.payment.model.Payment;
import com.gdgguadalajara.pos.payment.model.dto.CreatePaymentRequest;
import com.gdgguadalajara.pos.ticket.application.PayTicket;
import com.gdgguadalajara.pos.ticket.model.Ticket;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class CreatePayment {

    private final PayTicket payTicket;

    public Payment run(UUID ticketid, CreatePaymentRequest createPaymentRequest) {
        var payment = new Payment();
        var ticket = Ticket.<Ticket>findById(ticketid);
        if (ticket == null)
            throw DomainException.notFound("Ticket no encontrado");
        if(ticket.items.size() == 0)
            throw DomainException.badRequest("El ticket no tiene items");
        payment.ticket = ticket;
        payment.amount = createPaymentRequest.amount();
        payment.method = createPaymentRequest.method();
        payment.externalReference = createPaymentRequest.externalReference();
        payment.changeGiven = ticket.totalAmount.subtract(createPaymentRequest.amount());
        payment.persistAndFlush();
        var payments = Payment.<Payment>find("ticket.id", ticket.id).list();
        var totalPayments = payments.stream().map(p -> p.amount).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalPayments.compareTo(ticket.totalAmount) >= 0)
            payTicket.run(ticket);
        return payment;
    }
}
