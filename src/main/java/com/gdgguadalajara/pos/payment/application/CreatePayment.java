package com.gdgguadalajara.pos.payment.application;

import java.math.BigDecimal;

import com.gdgguadalajara.pos.cashsession.model.CashSession;
import com.gdgguadalajara.pos.cashsession.model.CashSessionStatus;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.payment.model.Payment;
import com.gdgguadalajara.pos.payment.model.dto.CreatePaymentRequest;
import com.gdgguadalajara.pos.ticket.application.PayTicket;
import com.gdgguadalajara.pos.ticket.model.Ticket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
@Transactional
public class CreatePayment {

    private final PayTicket payTicket;

    public Payment run(Ticket ticket, CashSession cashSession, CreatePaymentRequest createPaymentRequest) {
        if (cashSession.status.equals(CashSessionStatus.CLOSED))
            throw DomainException.badRequest("La sesión de caja no está abierta");
        if (ticket.items.size() == 0)
            throw DomainException.badRequest("El ticket no tiene items");
        var payment = new Payment();
        payment.ticket = ticket;
        payment.cashSession = cashSession;
        payment.amount = createPaymentRequest.amount();
        payment.method = createPaymentRequest.method();
        payment.externalReference = createPaymentRequest.externalReference();
        if (createPaymentRequest.amount().compareTo(ticket.totalAmount) > 0)
            payment.changeGiven = createPaymentRequest.amount().subtract(ticket.totalAmount);
        payment.persistAndFlush();
        var payments = Payment.<Payment>find("ticket.id", ticket.id).list();
        var totalPayments = payments.stream().map(p -> p.amount).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalPayments.compareTo(ticket.totalAmount) >= 0)
            payTicket.run(ticket);
        return payment;
    }
}
