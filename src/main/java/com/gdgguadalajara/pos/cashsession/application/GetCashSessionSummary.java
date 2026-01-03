package com.gdgguadalajara.pos.cashsession.application;

import java.math.BigDecimal;

import com.gdgguadalajara.pos.cashsession.model.dto.GetCashSessionSummaryResponse;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.ticket.model.Ticket;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class GetCashSessionSummary {

    private final GetCurrentCashSession getCurrentCashSession;

    public GetCashSessionSummaryResponse run() {
        var currentCashSession = getCurrentCashSession.run();
        if (currentCashSession == null)
            throw DomainException.forbidden("No hay una sesión de caja abierta");
        ResponseHelper stats = Ticket.getEntityManager()
                .createQuery("SELECT COUNT(t), SUM(t.totalAmount) FROM Ticket t " +
                        "WHERE t.status = 'PAID' AND t.closedAt BETWEEN :opened AND NOW()",
                        ResponseHelper.class)
                .setParameter("opened", currentCashSession.openingDate)
                .getSingleResult();
        BigDecimal total = (stats.total != null) ? (BigDecimal) stats.total : BigDecimal.ZERO;
        return new GetCashSessionSummaryResponse(
                currentCashSession.id,
                currentCashSession.openingDate,
                currentCashSession.openedBy,
                currentCashSession.initialBalance,
                total,
                stats.count,
                currentCashSession.initialBalance.add(total));
    }

    private record ResponseHelper(
            Long count,
            BigDecimal total) {
    }
}
