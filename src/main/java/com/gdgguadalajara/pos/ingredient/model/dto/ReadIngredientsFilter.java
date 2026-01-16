package com.gdgguadalajara.pos.ingredient.model.dto;

import java.util.UUID;

import com.gdgguadalajara.pos.common.model.dto.PaginationRequestParams;

import jakarta.ws.rs.QueryParam;

public class ReadIngredientsFilter extends PaginationRequestParams {

    @QueryParam("id")
    public UUID id;

    @QueryParam("name")
    public String name;
}
