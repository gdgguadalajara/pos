package com.gdgguadalajara.pos.businessprofile.model.dto;

public record UpdateBusinessProfileRequest(
        String name,
        String legalName,
        String taxId,
        String address,
        String phone,
        String email,
        String website,
        String ticketFooter,
        String logoUrl) {
}
