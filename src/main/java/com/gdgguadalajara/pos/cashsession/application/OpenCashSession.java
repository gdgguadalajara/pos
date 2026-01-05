package com.gdgguadalajara.pos.cashsession.application;

import java.time.LocalDateTime;

import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.cashsession.model.CashSession;
import com.gdgguadalajara.pos.cashsession.model.CashSessionStatus;
import com.gdgguadalajara.pos.cashsession.model.dto.OpenCashSessionRequest;
import com.gdgguadalajara.pos.common.model.DomainException;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class OpenCashSession {

    private final GetCurrentSession getCurrentSession;
    private final GetCurrentCashSession getCurrentCashSession;

    public CashSession run(OpenCashSessionRequest request) {
        var sessionAccount = getCurrentSession.run();
        var currentCashSession = getCurrentCashSession.run();
        if (currentCashSession != null)
            throw DomainException.forbidden("Ya hay una sesión de caja abierta");
        var cashSession = new CashSession();
        cashSession.openedBy = sessionAccount.user;
        cashSession.initialBalance = request.initialBalance();
        cashSession.openingDate = LocalDateTime.now();
        cashSession.status = CashSessionStatus.OPEN;
        cashSession.persistAndFlush();
        return cashSession;
    }
}
