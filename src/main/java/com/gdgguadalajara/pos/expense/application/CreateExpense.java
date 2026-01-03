package com.gdgguadalajara.pos.expense.application;

import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.cashsession.application.GetCurrentCashSession;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.expense.model.Expense;
import com.gdgguadalajara.pos.expense.model.dto.CreateExpenseRequest;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class CreateExpense {

    private final GetCurrentSession getCurrentSession;
    private final GetCurrentCashSession getCurrentCashSession;

    public Expense run(CreateExpenseRequest request) {
        var currentCashSession = getCurrentCashSession.run();
        if (currentCashSession == null)
            throw DomainException.forbidden("No hay una sesión de caja abierta");
        var expense = new Expense();
        expense.createdBy = getCurrentSession.run().user;
        expense.description = request.description();
        expense.amount = request.amount();
        expense.category = request.category();
        expense.persistAndFlush();
        return expense;
    }
}
