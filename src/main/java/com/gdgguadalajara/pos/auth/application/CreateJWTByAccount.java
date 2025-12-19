package com.gdgguadalajara.pos.auth.application;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.gdgguadalajara.pos.account.model.Account;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateJWTByAccount {

    @ConfigProperty(name = "mp.jwt.verify.issuer", defaultValue = "https://pos.gdgguadalajara.com/issuer")
    public String issuer;

    @ConfigProperty(name = "mp.jwt.verify.audiences", defaultValue = "com.gdgguadalajara.pos")
    public List<String> audiences;

    public String run(Account account) {
        return Jwt
                .issuer(issuer)
                .audience(Set.copyOf(audiences))
                .subject(account.id.toString())
                .groups(account.role.toString())
                .expiresIn(Duration.ofDays(7))
                .sign();
    }

}
