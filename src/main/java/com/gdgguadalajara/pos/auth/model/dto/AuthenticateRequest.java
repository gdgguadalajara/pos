package com.gdgguadalajara.pos.auth.model.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticateRequest(
        @NotBlank
        String email,
        @NotBlank
        String password) {
}
