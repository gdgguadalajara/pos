package com.gdgguadalajara.pos.account.application;

import java.time.LocalDateTime;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

import com.gdgguadalajara.pos.account.model.Account;
import com.gdgguadalajara.pos.account.model.AccountStatus;
import com.gdgguadalajara.pos.account.model.dto.ActivateAccountRequest;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.invitation.model.Invitation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
@Transactional
public class ActivateAccount {

    public Account run(ActivateAccountRequest request) {
        var invitation = Invitation.<Invitation>find("token", request.invitationToken()).firstResult();
        if (invitation == null)
            throw DomainException.notFound("Invitación no encontrada");
        if (invitation.usedAt != null)
            throw DomainException.forbidden("Invitación ya usada");
        if (invitation.expiresAt.isBefore(LocalDateTime.now()))
            throw DomainException.forbidden("Invitación expirada");
        var account = invitation.user.account;
        if (!invitation.user.account.status.equals(AccountStatus.PENDING_SETUP))
            throw DomainException.forbidden("Esta cuenta ya ah sido activada");
        var passwordSHA = new DigestUtils(MessageDigestAlgorithms.SHA3_256).digestAsHex(request.password());
        invitation.usedAt = LocalDateTime.now();
        account.status = AccountStatus.ACTIVE;
        account.password = passwordSHA;
        invitation.persistAndFlush();
        account.persistAndFlush();
        return account;
    }
}
