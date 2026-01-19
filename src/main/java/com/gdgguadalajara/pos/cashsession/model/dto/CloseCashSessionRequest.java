package com.gdgguadalajara.pos.cashsession.model.dto;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CloseCashSessionRequest(
        @Positive
        BigDecimal reportedBalance,
        String note) {
}
