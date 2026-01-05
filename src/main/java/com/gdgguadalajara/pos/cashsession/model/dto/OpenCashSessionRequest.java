package com.gdgguadalajara.pos.cashsession.model.dto;

import java.math.BigDecimal;

public record OpenCashSessionRequest(
        BigDecimal initialBalance) {
}
