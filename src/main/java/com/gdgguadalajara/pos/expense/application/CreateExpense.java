package com.gdgguadalajara.pos.expense.application;

import java.util.UUID;

import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.cashsession.model.CashSession;
import com.gdgguadalajara.pos.cashsession.model.CashSessionStatus;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.expense.model.Expense;
import com.gdgguadalajara.pos.expense.model.dto.CreateExpenseRequest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
@Transactional
public class CreateExpense {

    private final GetCurrentSession getCurrentSession;

    public Expense run(UUID cashsessionId, CreateExpenseRequest request) {
        var cashSession = CashSession.<CashSession>findById(cashsessionId);
        if (cashSession == null)
            throw DomainException.notFound("La sesión de caja no existe");
        if (cashSession.status.equals(CashSessionStatus.CLOSED))
            throw DomainException.badRequest("La sesión de caja no está abierta");
        var expense = new Expense();
        expense.createdBy = getCurrentSession.run().user;
        expense.description = request.description();
        expense.cashSession = cashSession;
        expense.amount = request.amount();
        expense.category = request.category();
        expense.persistAndFlush();
        return expense;
    }
}
