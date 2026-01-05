package com.gdgguadalajara.pos.expense;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.common.PageBuilder;
import com.gdgguadalajara.pos.common.model.PaginatedResponse;
import com.gdgguadalajara.pos.expense.application.CreateExpense;
import com.gdgguadalajara.pos.expense.model.Expense;
import com.gdgguadalajara.pos.expense.model.dto.CreateExpenseRequest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;

@Path("/api/expenses")
@AllArgsConstructor
public class ExpenseResource {

    private final CreateExpense createExpense;

    @GET
    @RolesAllowed({ AccountRole.ADMIN_ROLE, AccountRole.CASHIER_ROLE })
    public PaginatedResponse<Expense> read(
            @QueryParam("page") @DefaultValue("1") @Positive @Valid Integer page,
            @QueryParam("size") @DefaultValue("10") @Positive @Max(100) @Valid Integer size) {
        return PageBuilder.of(Expense.findAll(), page, size);
    }

    @POST
    @RolesAllowed({ AccountRole.ADMIN_ROLE, AccountRole.CASHIER_ROLE })
    @Transactional
    public Expense create(CreateExpenseRequest request) {
        return createExpense.run(request);
    }

}
