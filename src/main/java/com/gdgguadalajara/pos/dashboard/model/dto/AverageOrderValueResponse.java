package com.gdgguadalajara.pos.dashboard.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RegisterForReflection
@AllArgsConstructor
@NoArgsConstructor
public class AverageOrderValueResponse {
    public LocalDate date;
    public BigDecimal totalAmount;
    public Long ticketCount;
    public Double averageAmount;
}
