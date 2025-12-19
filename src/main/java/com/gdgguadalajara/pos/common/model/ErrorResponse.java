package com.gdgguadalajara.pos.common.model;

public record ErrorResponse(
        String message,
        int code,
        String type) {
}
