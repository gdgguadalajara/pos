package com.gdgguadalajara.pos.businessprofile.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateBusinessProfileRequest(
        @NotBlank
        String name,
        @NotBlank
        String legalName,
        @NotBlank
        String taxId,
        @NotBlank
        String address,
        /*
         * ITU E.123 and E.164 phone number formats
         * */
        @Pattern(regexp = "^\\+\\d{1,3}\\s\\d{2,3}\\s\\d{2,3}\\s\\d{4}|^\\+\\d{1,3}\\s\\d{1,14}(\\s\\d{1,13})?|^\\(\\d{3}\\)\\s\\d{3}\\s\\d{4}?")
        String phone,
        @Email
        @NotBlank
        String email,

        /* pattern can be empty ↓*/
        @Pattern(regexp = "^(?:(https:\\\\/\\\\/www\\\\.|http:\\\\/\\\\/www\\\\.|https:\\\\/\\\\/|http:\\\\/\\\\/)?[a-zA-Z0-9]{2,}(\\\\.[a-zA-Z0-9]{2,})(\\\\.[a-zA-Z0-9]{2,})?)?$")
        String website,

        String ticketFooter,
        @Pattern(regexp = "^(?:(https:\\\\/\\\\/www\\\\.|http:\\\\/\\\\/www\\\\.|https:\\\\/\\\\/|http:\\\\/\\\\/)?[a-zA-Z0-9]{2,}(\\\\.[a-zA-Z0-9]{2,})(\\\\.[a-zA-Z0-9]{2,})?)?$")
        String logoUrl
)
{}
