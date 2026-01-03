package com.gdgguadalajara.pos.cashsession.application;

import com.gdgguadalajara.pos.cashsession.model.CashSession;
import com.gdgguadalajara.pos.cashsession.model.CashSessionStatus;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GetCurrentCashSession {

    public CashSession run() {
        return CashSession.find("status", CashSessionStatus.OPEN).firstResult();
    }
}
