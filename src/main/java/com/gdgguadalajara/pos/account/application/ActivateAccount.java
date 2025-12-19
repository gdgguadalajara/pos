package com.gdgguadalajara.pos.account.application;

import java.time.LocalDateTime;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

import com.gdgguadalajara.pos.account.model.Account;
import com.gdgguadalajara.pos.account.model.AccountStatus;
import com.gdgguadalajara.pos.account.model.dto.ActivateAccountRequest;
import com.gdgguadalajara.pos.invitation.model.Invitation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class ActivateAccount {

    public Account run(ActivateAccountRequest request) {
        var passwordSHA = new DigestUtils(MessageDigestAlgorithms.SHA3_256).digestAsHex(request.password());
        var invitation = Invitation.<Invitation>find("token", request.invitationToken()).firstResult();
        if (invitation == null)
            throw new NotFoundException("Invitación no encontrada");
        if (invitation.usedAt != null)
            throw new ForbiddenException("Invitación ya usada");
        if (invitation.expiresAt.isBefore(LocalDateTime.now()))
            throw new ForbiddenException("Invitación expirada");
        var account = invitation.user.account;
        if (!invitation.user.account.status.equals(AccountStatus.PENDING_SETUP))
            throw new ForbiddenException("Esta cuenta ya ah sido activada");
        invitation.usedAt = LocalDateTime.now();
        account.status = AccountStatus.ACTIVE;
        account.password = passwordSHA;
        invitation.persistAndFlush();
        account.persistAndFlush();
        return account;
    }
}
