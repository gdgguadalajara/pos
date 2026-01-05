package com.gdgguadalajara.pos.cashsession.application;

import java.math.BigDecimal;

import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.cashsession.model.CashSession;
import com.gdgguadalajara.pos.cashsession.model.CashSessionStatus;
import com.gdgguadalajara.pos.cashsession.model.dto.GetCashSessionSummaryResponse;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.expense.model.Expense;
import com.gdgguadalajara.pos.payment.model.Payment;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class GetCashSessionSummary {

    private final GetCurrentSession getCurrentSession;

    public GetCashSessionSummaryResponse run() {
        var currentCashSession = CashSession
                .<CashSession>find("openedBy.id = ?1 AND status = ?2", getCurrentSession.run().user.id,
                        CashSessionStatus.OPEN)
                .firstResult();
        if (currentCashSession == null)
            throw DomainException.forbidden("No tienes una sesión de caja abierta");
        var sales = Payment.getEntityManager()
                .createQuery(
                        "SELECT SUM(amount - changeGiven) FROM Payment WHERE cashSession.id = :cashsession",
                        BigDecimal.class)
                .setParameter("cashsession", currentCashSession.id)
                .getSingleResult();
        sales = (sales != null) ? (BigDecimal) sales : BigDecimal.ZERO;
        var totalExpenses = Expense.getEntityManager()
                .createQuery("SELECT SUM(amount) FROM Expense WHERE cashSession.id = :cashsession",
                        BigDecimal.class)
                .setParameter("cashsession", currentCashSession.id)
                .getSingleResult();
        totalExpenses = (totalExpenses != null) ? totalExpenses : BigDecimal.ZERO;
        return new GetCashSessionSummaryResponse(
                currentCashSession.id,
                currentCashSession.openingDate,
                currentCashSession.openedBy,
                currentCashSession.initialBalance,
                totalExpenses,
                sales,
                currentCashSession.initialBalance.add(sales).subtract(totalExpenses));
    }
}
