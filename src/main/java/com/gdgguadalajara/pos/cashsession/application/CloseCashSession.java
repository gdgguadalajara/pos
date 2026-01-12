package com.gdgguadalajara.pos.cashsession.application;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.cashsession.model.CashSession;
import com.gdgguadalajara.pos.cashsession.model.CashSessionStatus;
import com.gdgguadalajara.pos.cashsession.model.dto.CloseCashSessionRequest;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.expense.model.Expense;
import com.gdgguadalajara.pos.payment.model.Payment;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class CloseCashSession {

    private final GetCurrentSession getCurrentSession;

    public CashSession run(CloseCashSessionRequest request) {
        var currentSession = getCurrentSession.run();
        var sessionAccount = getCurrentSession.run();
        var currentCashSession = CashSession
                .<CashSession>find("openedBy.id = ?1 AND status = ?2", sessionAccount.user.id, CashSessionStatus.OPEN)
                .firstResult();
        if (currentCashSession == null)
            throw DomainException.forbidden("No tienes una sesión de caja abierta");
        if (!currentSession.user.account.role.equals(AccountRole.ADMIN)
                && !currentSession.user.equals(currentCashSession.openedBy))
            throw DomainException.forbidden("No tienes permiso para cerrar esta sesión");
        var sales = Payment.getEntityManager()
                .createQuery(
                        "SELECT SUM(amount - changeGiven) FROM Payment WHERE cashSession.id = :cashsession",
                        BigDecimal.class)
                .setParameter("cashsession", currentCashSession.id)
                .getSingleResult();
        var totalExpenses = Expense.getEntityManager()
                .createQuery("SELECT SUM(amount) FROM Expense WHERE cashSession.id = :cashsession",
                        BigDecimal.class)
                .setParameter("cashsession", currentCashSession.id)
                .getSingleResult();
        totalExpenses = (totalExpenses != null) ? totalExpenses : BigDecimal.ZERO;
        sales = (sales != null) ? sales : BigDecimal.ZERO;
        var expected = currentCashSession.initialBalance.add(sales).subtract(totalExpenses);
        currentCashSession.totalSales = sales;
        currentCashSession.totalExpenses = totalExpenses;
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
