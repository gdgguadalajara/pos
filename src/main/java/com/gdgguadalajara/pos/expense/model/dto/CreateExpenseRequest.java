package com.gdgguadalajara.pos.expense.model.dto;

import java.math.BigDecimal;

import com.gdgguadalajara.pos.expense.model.ExpenseCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateExpenseRequest(
        @NotBlank String description,
        @NotNull @Positive BigDecimal amount,
        @NotNull ExpenseCategory category) {

}
