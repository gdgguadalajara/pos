package com.gdgguadalajara.pos.invitation.model.dto;

import com.gdgguadalajara.pos.account.model.AccountRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateInvitationRequest(
        @NotBlank @Pattern(regexp = "^\\p{L}+(?: \\p{L}+)*$", message = "El nombre solo puede contener letras") String name,
        @NotBlank @Email String email,
        @NotNull AccountRole role) {
}
