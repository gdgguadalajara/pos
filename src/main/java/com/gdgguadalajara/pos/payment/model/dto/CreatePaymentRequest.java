package com.gdgguadalajara.pos.payment.model.dto;

import java.math.BigDecimal;

import com.gdgguadalajara.pos.payment.model.PaymentMethod;

public record CreatePaymentRequest(
        BigDecimal amount,
        PaymentMethod method,
        String externalReference) {
}
