package com.gdgguadalajara.pos.category.model.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

public record UpdateCategoryRequest(
        @NotBlank
        String name,
        @NotBlank
        String description,
        @NotNull
        Boolean isEnabled,
        @FutureOrPresent
        LocalDate availableFrom,
        @FutureOrPresent
        LocalDate availableUntil,
        @NotNull
        LocalTime availableFromTime,
        @NotNull
        LocalTime availableUntilTime,
        @Size(min = 0, max = 7)
        Set<DayOfWeek> availableDays,
        @NotNull
        UUID productionCenterId) {
}
