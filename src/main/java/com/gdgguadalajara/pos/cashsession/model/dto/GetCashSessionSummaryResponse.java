package com.gdgguadalajara.pos.cashsession.model.dto;

import java.util.UUID;

import com.gdgguadalajara.pos.user.model.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GetCashSessionSummaryResponse(
        UUID cashSessionId,
        LocalDateTime openingDate,
        User openedBy,
        BigDecimal initialBalance,
        BigDecimal totalExpenses,
        BigDecimal totalSales,
        Long salesCount,
        BigDecimal expectedBalance) {
}
