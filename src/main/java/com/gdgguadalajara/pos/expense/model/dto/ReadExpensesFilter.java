package com.gdgguadalajara.pos.expense.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.gdgguadalajara.pos.common.model.dto.PaginationRequestParams;
import com.gdgguadalajara.pos.expense.model.ExpenseCategory;

import jakarta.ws.rs.QueryParam;

public class ReadExpensesFilter extends PaginationRequestParams {

    @QueryParam("id")
    public UUID id;

    @QueryParam("createdBy")
    public String createdBy;

    @QueryParam("amount")
    public BigDecimal amount;

    @QueryParam("category")
    public ExpenseCategory category;
    
    @QueryParam("createdAt")
    public LocalDate createdAt;
}
