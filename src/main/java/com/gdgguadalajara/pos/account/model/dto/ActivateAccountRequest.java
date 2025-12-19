package com.gdgguadalajara.pos.account.model.dto;

import jakarta.validation.constraints.NotBlank;

public record ActivateAccountRequest(
        @NotBlank String invitationToken,
        @NotBlank String password) {
}
