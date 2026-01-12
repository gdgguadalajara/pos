package com.gdgguadalajara.pos.cashsession.application;

import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.cashsession.model.CashSession;
import com.gdgguadalajara.pos.cashsession.model.CashSessionStatus;
import com.gdgguadalajara.pos.common.model.DomainException;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class GetCurrentCashSession {

    private final GetCurrentSession getCurrentSession;

    public CashSession run() {
        var currentCashSession = CashSession
                .<CashSession>find("openedBy.id = ?1 AND status = ?2", getCurrentSession.run().user.id,
                        CashSessionStatus.OPEN)
                .firstResult();
        if (currentCashSession == null)
            throw DomainException.forbidden("No tienes una sesión de caja abierta");
        return currentCashSession;
    }
}
