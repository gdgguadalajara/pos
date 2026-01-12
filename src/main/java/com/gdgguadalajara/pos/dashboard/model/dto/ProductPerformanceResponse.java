package com.gdgguadalajara.pos.dashboard.model.dto;

import java.math.BigDecimal;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RegisterForReflection
@AllArgsConstructor
@NoArgsConstructor
public class ProductPerformanceResponse {
	public String productName;
	public Long quantity;
	public BigDecimal totalRevenue;
}
