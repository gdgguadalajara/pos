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

    public CashSession run(OpenCashSessionRequest request) {
        var sessionAccount = getCurrentSession.run();
        var cashSession = CashSession
                .<CashSession>find("openedBy.id = ?1 AND status = ?2", sessionAccount.user.id, CashSessionStatus.OPEN)
                .firstResult();
        if (cashSession != null)
            throw DomainException.forbidden("Ya tienes una sesión de caja abierta");
        cashSession = new CashSession();
        cashSession.openedBy = sessionAccount.user;
        cashSession.initialBalance = request.initialBalance();
        cashSession.openingDate = LocalDateTime.now();
        cashSession.status = CashSessionStatus.OPEN;
        cashSession.persistAndFlush();
        return cashSession;
    }
}
