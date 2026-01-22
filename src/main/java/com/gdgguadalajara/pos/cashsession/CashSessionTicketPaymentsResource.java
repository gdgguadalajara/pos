package com.gdgguadalajara.pos.cashsession;

import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.cashsession.model.CashSession;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.payment.application.CreatePayment;
import com.gdgguadalajara.pos.payment.model.Payment;
import com.gdgguadalajara.pos.payment.model.dto.CreatePaymentRequest;
import com.gdgguadalajara.pos.ticket.model.Ticket;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@Path("/api/cash-sessions/{id}")
@AllArgsConstructor
public class CashSessionTicketPaymentsResource {

    private final CreatePayment createPayment;

    @POST
    @Path("/tickets/{ticketId}/payments")
    @RolesAllowed({ AccountRole.ADMIN_ROLE, AccountRole.CASHIER_ROLE })
    public Payment payTicket(UUID id, UUID ticketId, CreatePaymentRequest createPaymentRequest) {
        var cashSession = CashSession.<CashSession>findById(id);
        if (cashSession == null)
            throw DomainException.notFound("La sesión de caja no existe");
        var ticket = Ticket.<Ticket>findById(ticketId);
        if (ticket == null)
            throw DomainException.notFound("El ticket no existe");
        return createPayment.run(ticket, cashSession, createPaymentRequest);
    }
}
