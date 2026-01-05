package com.gdgguadalajara.pos.cashsession.model.dto;

import java.math.BigDecimal;

public record CloseCashSessionRequest(
        BigDecimal reportedBalance,
        String note) {
}
