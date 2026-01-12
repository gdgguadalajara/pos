package com.gdgguadalajara.pos.cashsession;

import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.cashsession.model.CashSession;
import com.gdgguadalajara.pos.common.model.DomainException;
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

@Path("/api/cash-sessions/{id}/expenses")
@AllArgsConstructor
public class CashSessionExpenseResource {

    private final GetCurrentSession getCurrentSession;
    private final CreateExpense createExpense;

    @POST
    @RolesAllowed({ AccountRole.ADMIN_ROLE, AccountRole.CASHIER_ROLE })
    @Transactional
    public Expense createExpense(UUID id, CreateExpenseRequest request) {
        return createExpense.run(id, request);
    }

    @GET
    @RolesAllowed({ AccountRole.ADMIN_ROLE, AccountRole.CASHIER_ROLE })
    public PaginatedResponse<Expense> read(UUID id, @BeanParam @Valid ReadExpensesFilter filter) {
        var cashSession = CashSession.<CashSession>findById(id);
        if (cashSession == null)
            throw DomainException.notFound("La sesión de caja no existe");
        var criteria = PanacheCriteria.of(Expense.class)
                .eq("id", filter.id)
                .eq("cashSession.id", cashSession.id)
                .eq("category", filter.category)
                .ge("amount", filter.amount)
                .page(filter.page, filter.size)
                .orderBy(filter.sort);
        var session = getCurrentSession.run();
        if (session.role.equals(AccountRole.ADMIN))
            criteria.like("createdBy.name", filter.createdBy);
        if (session.role.equals(AccountRole.CASHIER))
            criteria.eq("createdBy.id", session.user.id);
        if (filter.createdAt != null)
            criteria.gt("createdAt", filter.createdAt.atStartOfDay())
                    .lt("createdAt", filter.createdAt.plusDays(1).atStartOfDay());
        return criteria.getResult();
    }
}
