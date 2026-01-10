package com.gdgguadalajara.pos.dashboard.model.dto;

import java.time.LocalDateTime;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public class ProductPerformanceRequest {

	@QueryParam("start")
	public LocalDateTime start;

	@QueryParam("end")
	public LocalDateTime end;

	@QueryParam("limit")
	@DefaultValue("10")
	public Integer limit;

	@QueryParam("isAscending")
	@DefaultValue("true")
	public Boolean isAscending;
}