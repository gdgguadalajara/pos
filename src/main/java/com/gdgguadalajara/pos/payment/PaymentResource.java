package com.gdgguadalajara.pos.payment;

import java.util.UUID;

import com.gdgguadalajara.pos.payment.application.CreatePayment;
import com.gdgguadalajara.pos.payment.model.Payment;
import com.gdgguadalajara.pos.payment.model.dto.CreatePaymentRequest;

import io.quarkus.security.Authenticated;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@Path("/api/payments")
@AllArgsConstructor
public class PaymentResource {

    private final CreatePayment createPayment;

    @POST
    @Authenticated
    @Path("/{ticketId}")
    public Payment create(UUID ticketId, CreatePaymentRequest request) {
        return createPayment.run(ticketId, request);
    }
}
