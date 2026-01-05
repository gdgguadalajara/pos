package com.gdgguadalajara.pos.payment;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.common.model.PaginatedResponse;
import com.gdgguadalajara.pos.common.util.PanacheCriteria;
import com.gdgguadalajara.pos.payment.model.Payment;
import com.gdgguadalajara.pos.payment.model.dto.ReadPaymentsFilter;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@Path("/api/payments")
@AllArgsConstructor
public class PaymentResource {

    @GET
    @RolesAllowed({ AccountRole.ADMIN_ROLE, AccountRole.CASHIER_ROLE })
    public PaginatedResponse<Payment> payTicket(@BeanParam @Valid ReadPaymentsFilter filter) {
        var criteria = PanacheCriteria.of(Payment.class)
                .eq("id", filter.id)
                .eq("ticket.id", filter.ticketId)
                .eq("cashSession.id", filter.cashSessionId)
                .ge("amount", filter.amount)
                .eq("method", filter.method)
                .like("externalReference", filter.externalReference)
                .page(filter.page, filter.size)
                .orderBy(filter.sort);
        if (filter.createdAt != null)
            criteria.gt("createdAt", filter.createdAt.atStartOfDay())
                    .lt("createdAt", filter.createdAt.plusDays(1).atStartOfDay());
        return criteria.getResult();
    }
}
