package com.gdgguadalajara.pos.invitation.application;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import com.gdgguadalajara.pos.account.model.Account;
import com.gdgguadalajara.pos.account.model.AccountStatus;
import com.gdgguadalajara.pos.invitation.model.Invitation;
import com.gdgguadalajara.pos.invitation.model.dto.CreateInvitationRequest;
import com.gdgguadalajara.pos.user.model.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
@Transactional
public class CreateInvitation {

    public Invitation run(CreateInvitationRequest request) {
        var user = new User();
        user.name = request.name();
        user.email = request.email();
        user.persistAndFlush();

        var account = new Account();
        account.username = UUID.randomUUID().toString();
        account.status = AccountStatus.PENDING_SETUP;
        account.role = request.role();
        account.user = user;
        account.persistAndFlush();

        var invitation = new Invitation();
        invitation.user = user;
        invitation.token = UUID.randomUUID().toString();
        invitation.expiresAt = LocalDateTime.now().plus(Duration.ofDays(7));
        invitation.persistAndFlush();

        return invitation;
    }
}
