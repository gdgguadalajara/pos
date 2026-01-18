package com.gdgguadalajara.pos.cashsession.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OpenCashSessionRequest(
        @Positive
        @NotNull
        BigDecimal initialBalance) {
}
