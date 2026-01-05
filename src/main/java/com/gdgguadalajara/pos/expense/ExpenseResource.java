package com.gdgguadalajara.pos.expense;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.common.model.PaginatedResponse;
import com.gdgguadalajara.pos.common.util.PanacheCriteria;
import com.gdgguadalajara.pos.expense.model.Expense;
import com.gdgguadalajara.pos.expense.model.dto.ReadExpensesFilter;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@Path("/api/expenses")
@AllArgsConstructor
public class ExpenseResource {

    private final GetCurrentSession getCurrentSession;

    @GET
    @RolesAllowed({ AccountRole.ADMIN_ROLE, AccountRole.CASHIER_ROLE })
    public PaginatedResponse<Expense> read(@BeanParam @Valid ReadExpensesFilter filter) {
        var session = getCurrentSession.run();
        var criteria = PanacheCriteria.of(Expense.class)
                .eq("id", filter.id)
                .eq("cashSession.id", filter.cashSessionId)
                .eq("category", filter.category)
                .ge("amount", filter.amount)
                .page(filter.page, filter.size)
                .orderBy(filter.sort);
        if (filter.createdAt != null)
            criteria.gt("createdAt", filter.createdAt.atStartOfDay())
                    .lt("createdAt", filter.createdAt.plusDays(1).atStartOfDay());
        if (session.role.equals(AccountRole.ADMIN))
            criteria.like("createdBy.name", filter.createdBy);
        if (session.role.equals(AccountRole.CASHIER))
            criteria.eq("createdBy.id", session.user.id);
        return criteria.getResult();
    }
}
