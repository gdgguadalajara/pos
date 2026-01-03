package com.gdgguadalajara.pos.cashsession.application;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.cashsession.model.CashSession;
import com.gdgguadalajara.pos.cashsession.model.CashSessionStatus;
import com.gdgguadalajara.pos.cashsession.model.dto.CloseCashSessionRequest;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.ticket.model.Ticket;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class CloseCashSession {

    private final GetCurrentSession getCurrentSession;
    private final GetCurrentCashSession getCurrentCashSession;

    public CashSession run(CloseCashSessionRequest request) {
        var currentSession = getCurrentSession.run();
        var currentCashSession = getCurrentCashSession.run();
        if (currentCashSession == null)
            throw DomainException.forbidden("No hay una sesión de caja abierta");
        if (!currentSession.user.account.role.equals(AccountRole.ADMIN)
                && !currentSession.user.equals(currentCashSession.openedBy))
            throw DomainException.forbidden("No tienes permiso para cerrar esta sesión");
        var sales = Ticket.getEntityManager()
                .createQuery(
                        "SELECT SUM(t.totalAmount) FROM Ticket t WHERE t.status = 'PAID' AND t.closedAt BETWEEN :opened AND NOW()",
                        BigDecimal.class)
                .setParameter("opened", currentCashSession.openingDate)
                .getSingleResult();
        var expected = currentCashSession.initialBalance.add((sales != null) ? (BigDecimal) sales : BigDecimal.ZERO);
        currentCashSession.totalSales = sales;
        currentCashSession.closingDate = LocalDateTime.now();
        currentCashSession.closedBy = currentSession.user;
        currentCashSession.reportedBalance = request.reportedBalance();
        currentCashSession.status = CashSessionStatus.CLOSED;
        currentCashSession.note = request.note();
        currentCashSession.difference = currentCashSession.reportedBalance.subtract(expected);
        currentCashSession.persistAndFlush();
        return currentCashSession;
    }
}
