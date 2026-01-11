package com.gdgguadalajara.pos.category.model.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

public record CreateCategoryRequest(
        String name,
        String description,
        Boolean isEnabled,
        LocalDate availableFrom,
        LocalDate availableUntil,
        LocalTime availableFromTime,
        LocalTime availableUntilTime,
        Set<DayOfWeek> availableDays,
        UUID productionCenterId) {
}
