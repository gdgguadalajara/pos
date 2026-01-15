package com.gdgguadalajara.pos.user.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gdgguadalajara.pos.account.model.Account;
import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.account.model.AccountStatus;
import com.gdgguadalajara.pos.user.model.User;

public record UserSnapshot(
        UUID id,
        String name,
        String email,
        AccountSnapshot account,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

    public UserSnapshot(User user) {
        this(user.id, user.name, user.email,
                new AccountSnapshot(user.account), user.createdAt, user.updatedAt);
    }

    public record AccountSnapshot(
            UUID id,
            String username,
            AccountStatus status,
            AccountRole role,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        public AccountSnapshot(Account account) {
            this(account.id, account.username,
                    account.status, account.role,
                    account.createdAt, account.updatedAt);
        }
    }
}
