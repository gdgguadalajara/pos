package com.gdgguadalajara.pos.product.model.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public record UpdateProductRequest(
        @NotBlank
        String name,
        @NotBlank
        String description,
        @NotNull
        @Positive
        BigDecimal price,
        @NotNull
        Boolean isEnabled,
        @NotNull
        LocalDate availableFrom,
        @FutureOrPresent
        LocalDate availableUntil,
        @NotNull
        LocalTime availableFromTime,
        @NotNull
        LocalTime availableUntilTime,
        @Size(min = 1, max = 7)
        Set<DayOfWeek> availableDays) {
}
