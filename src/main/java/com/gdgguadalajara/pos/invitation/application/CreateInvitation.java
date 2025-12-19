package com.gdgguadalajara.pos.invitation.application;

import java.time.Duration;
import java.time.LocalDateTime;

import com.gdgguadalajara.pos.account.model.Account;
import com.gdgguadalajara.pos.account.model.AccountStatus;
import com.gdgguadalajara.pos.invitation.model.Invitation;
import com.gdgguadalajara.pos.invitation.model.dto.CreateInvitationRequest;
import com.gdgguadalajara.pos.user.model.User;

import io.github.thibaultmeyer.cuid.CUID;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class CreateInvitation {

    public Invitation run(CreateInvitationRequest request) {
        var user = new User();
        user.name = request.name();
        user.email = request.email();
        user.persistAndFlush();

        var account = new Account();
        account.username = CUID.randomCUID2().toString();
        account.status = AccountStatus.PENDING_SETUP;
        account.role = request.role();
        account.user = user;
        account.persistAndFlush();

        var invitation = new Invitation();
        invitation.user = user;
        invitation.token = CUID.randomCUID2().toString();
        invitation.expiresAt = LocalDateTime.now().plus(Duration.ofDays(7));
        invitation.persistAndFlush();

        return invitation;
    }
}
