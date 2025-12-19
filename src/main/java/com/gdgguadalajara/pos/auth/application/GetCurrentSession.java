package com.gdgguadalajara.pos.auth.application;

import java.util.UUID;

import org.eclipse.microprofile.jwt.JsonWebToken;

import com.gdgguadalajara.pos.account.model.Account;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class GetCurrentSession {

    private final JsonWebToken jwt;

    public Account run() {
        var account = Account.<Account>findById(UUID.fromString(jwt.getSubject()));
        if (account == null)
            throw new NotFoundException("Account not found");
        return account;
    }
}
