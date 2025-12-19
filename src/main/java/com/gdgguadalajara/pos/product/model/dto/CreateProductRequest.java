package com.gdgguadalajara.pos.product.model.dto;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public record CreateProductRequest(
                String name,
                String description,
                BigDecimal price,
                Boolean isEnabled,
                LocalDate availableFrom,
                LocalDate availableUntil,
                LocalTime availableFromTime,
                LocalTime availableUntilTime,
                Set<DayOfWeek> availableDays) {
}
