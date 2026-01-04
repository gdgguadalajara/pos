package com.gdgguadalajara.pos.category.model.dto;

import java.time.DayOfWeek;
import java.util.Set;
import java.util.UUID;

import com.gdgguadalajara.pos.common.model.dto.PaginationRequestParams;

import jakarta.ws.rs.QueryParam;

public class ReadCategoriesFilter extends PaginationRequestParams {

    @QueryParam("id")
    public UUID id;

    @QueryParam("name")
    public String name;

    @QueryParam("isEnabled")
    public Boolean isEnabled;

    @QueryParam("availableDays")
    public Set<DayOfWeek> availableDays;
}
