package com.gdgguadalajara.pos.auth.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticateRequest(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password) {
}
