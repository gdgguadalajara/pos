package com.gdgguadalajara.pos.auth.application;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.gdgguadalajara.pos.account.model.Account;
import com.gdgguadalajara.pos.account.model.AccountStatus;
import com.gdgguadalajara.pos.auth.model.dto.AuthenticateRequest;
import com.gdgguadalajara.pos.auth.model.dto.AuthenticateResponse;
import com.gdgguadalajara.pos.common.model.DomainException;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class Authenticate {

    @ConfigProperty(name = "mp.jwt.verify.issuer", defaultValue = "https://pos.gdgguadalajara.com/issuer")
    public String issuer;

    @ConfigProperty(name = "mp.jwt.verify.audiences", defaultValue = "com.gdgguadalajara.pos")
    public List<String> audiences;

    public AuthenticateResponse run(AuthenticateRequest request) {
        var passwordSHA = new DigestUtils(MessageDigestAlgorithms.SHA3_256).digestAsHex(request.password());
        var account = Account
                .<Account>find("password = ?1 AND (username = ?2 OR user.email = ?2) ", passwordSHA, request.email())
                .firstResult();

        if (account == null)
            throw DomainException.badRequest("Error en usuario o contraseña");

        switch (account.status) {
            case AccountStatus.PENDING_SETUP:
                throw DomainException.badRequest("Pide tu link de invitacion para configurar tus credenciales");
            case AccountStatus.LOCKED:
                throw DomainException.badRequest("Cuenta bloqueada, contacta al administrador");
            case AccountStatus.DISABLED:
                throw DomainException.badRequest("Cuenta inhabilitada, contacta al administrador");
            case AccountStatus.ACTIVE:
                break;
        }

        var jwt = Jwt
                .issuer(issuer)
                .audience(Set.copyOf(audiences))
                .subject(account.id.toString())
                .groups(account.role.toString())
                .expiresIn(Duration.ofDays(7))
                .sign();

        return new AuthenticateResponse(jwt, account.user);
    }
}
