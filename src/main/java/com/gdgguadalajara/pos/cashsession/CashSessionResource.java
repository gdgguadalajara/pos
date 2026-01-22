package com.gdgguadalajara.pos.cashsession;

import java.math.BigDecimal;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.cashsession.application.CloseCashSession;
import com.gdgguadalajara.pos.cashsession.application.GetCashSessionSummary;
import com.gdgguadalajara.pos.cashsession.application.GetCurrentCashSession;
import com.gdgguadalajara.pos.cashsession.application.OpenCashSession;
import com.gdgguadalajara.pos.cashsession.model.CashSession;
import com.gdgguadalajara.pos.cashsession.model.dto.CloseCashSessionRequest;
import com.gdgguadalajara.pos.cashsession.model.dto.GetCashSessionSummaryResponse;
import com.gdgguadalajara.pos.cashsession.model.dto.OpenCashSessionRequest;
import com.gdgguadalajara.pos.cashsession.model.dto.ReadCashSessionFilter;
import com.gdgguadalajara.pos.common.model.PaginatedResponse;
import com.gdgguadalajara.pos.common.util.PanacheCriteria;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@Path("/api/cash-sessions")
@AllArgsConstructor
public class CashSessionResource {

    private final OpenCashSession openCashSession;
    private final CloseCashSession closeCashSession;
    private final GetCashSessionSummary getCashSessionSummary;
    private final GetCurrentCashSession getCurrentCashSession;

    @GET
    @RolesAllowed({ AccountRole.ADMIN_ROLE })
    public PaginatedResponse<CashSession> read(@BeanParam @Valid ReadCashSessionFilter filter) {
        var criteria = PanacheCriteria.of(CashSession.class)
                .eq("id", filter.id)
                .like("openedBy.name", filter.openedBy)
                .like("closedBy.name", filter.closedBy)
                .eq("status", filter.status)
                .page(filter.page, filter.size)
                .orderBy("openingDate");
        if (filter.openingDate != null)
            criteria.ge("openingDate", filter.openingDate.atStartOfDay())
                    .le("openingDate", filter.openingDate.plusDays(1).atStartOfDay());
        if (filter.closingDate != null)
            criteria.ge("closingDate", filter.closingDate.atStartOfDay())
                    .le("closingDate", filter.closingDate.plusDays(1).atStartOfDay());
        if (filter.negatives)
            criteria.lt("difference", BigDecimal.ZERO);
        return criteria.getResult();
    }

    @POST
    @Path("/open")
    @RolesAllowed({ AccountRole.CASHIER_ROLE })
    public CashSession open(OpenCashSessionRequest request) {
        return openCashSession.run(request);
    }

    @PUT
    @Path("/close")
    @RolesAllowed({ AccountRole.ADMIN_ROLE, AccountRole.CASHIER_ROLE })
    public CashSession close(CloseCashSessionRequest request) {
        return closeCashSession.run(request);
    }

    @GET
    @Path("/current")
    @RolesAllowed({ AccountRole.ADMIN_ROLE, AccountRole.CASHIER_ROLE })
    public CashSession current() {
        return getCurrentCashSession.run();
    }

    @GET
    @Path("/current/summary")
    @RolesAllowed({ AccountRole.ADMIN_ROLE, AccountRole.CASHIER_ROLE })
    public GetCashSessionSummaryResponse summary() {
        return getCashSessionSummary.run();
    }

}
