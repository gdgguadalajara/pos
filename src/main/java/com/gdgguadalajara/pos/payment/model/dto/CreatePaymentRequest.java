package com.gdgguadalajara.pos.payment.model.dto;

import java.math.BigDecimal;

import com.gdgguadalajara.pos.payment.model.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreatePaymentRequest(
        @Positive
        @NotNull
        BigDecimal amount,
        @NotNull
        PaymentMethod method,
        String externalReference) {
}
