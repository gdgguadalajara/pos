package com.gdgguadalajara.pos.expense;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.common.model.PaginatedResponse;
import com.gdgguadalajara.pos.common.util.PanacheCriteria;
import com.gdgguadalajara.pos.expense.application.CreateExpense;
import com.gdgguadalajara.pos.expense.model.Expense;
import com.gdgguadalajara.pos.expense.model.dto.CreateExpenseRequest;
import com.gdgguadalajara.pos.expense.model.dto.ReadExpensesFilter;

import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@Path("/api/expenses")
@AllArgsConstructor
public class ExpenseResource {

    private final CreateExpense createExpense;

    @GET
    @RolesAllowed({ AccountRole.ADMIN_ROLE, AccountRole.CASHIER_ROLE })
    public PaginatedResponse<Expense> read(@BeanParam @Valid ReadExpensesFilter filter) {
        var criteria = PanacheCriteria.of(Expense.class)
                .eq("id", filter.id)
                .like("createdBy.name", filter.createdBy)
                .eq("category", filter.category)
                .ge("amount", filter.amount)
                .page(filter.page, filter.size)
                .orderBy(filter.sort);
        if (filter.createdAt != null)
            criteria.gt("createdAt", filter.createdAt.atStartOfDay())
                    .lt("createdAt", filter.createdAt.plusDays(1).atStartOfDay());
        return criteria.getResult();
    }

    @POST
    @RolesAllowed({ AccountRole.ADMIN_ROLE, AccountRole.CASHIER_ROLE })
    @Transactional
    public Expense create(CreateExpenseRequest request) {
        return createExpense.run(request);
    }

}
