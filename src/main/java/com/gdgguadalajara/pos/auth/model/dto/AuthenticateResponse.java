package com.gdgguadalajara.pos.auth.model.dto;

import com.gdgguadalajara.pos.user.model.User;

public record AuthenticateResponse(
        String token,
        User user) {
}
