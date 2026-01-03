package com.gdgguadalajara.pos.cashsession;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.cashsession.application.CloseCashSession;
import com.gdgguadalajara.pos.cashsession.application.GetCashSessionSummary;
import com.gdgguadalajara.pos.cashsession.application.GetCurrentCashSession;
import com.gdgguadalajara.pos.cashsession.application.OpenCashSession;
import com.gdgguadalajara.pos.cashsession.model.CashSession;
import com.gdgguadalajara.pos.cashsession.model.dto.CloseCashSessionRequest;
import com.gdgguadalajara.pos.cashsession.model.dto.GetCashSessionSummaryResponse;
import com.gdgguadalajara.pos.cashsession.model.dto.OpenCashSessionRequest;
import com.gdgguadalajara.pos.common.PageBuilder;
import com.gdgguadalajara.pos.common.model.PaginatedResponse;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
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
    public PaginatedResponse<CashSession> read(
            @QueryParam("page") @DefaultValue("1") @Positive @Valid Integer page,
            @QueryParam("size") @DefaultValue("10") @Positive @Max(100) @Valid Integer size) {
        return PageBuilder.of(CashSession.findAll(), page, size);
    }

    @POST
    @Path("/open")
    @Transactional
    @RolesAllowed({ AccountRole.CASHIER_ROLE })
    public CashSession open(OpenCashSessionRequest request) {
        return openCashSession.run(request);
    }

    @PUT
    @Path("/close")
    @Transactional
    @RolesAllowed({ AccountRole.ADMIN_ROLE, AccountRole.CASHIER_ROLE })
    public CashSession close(CloseCashSessionRequest request) {
        return closeCashSession.run(request);
    }

    @GET
    @Path("/current")
    @Authenticated
    public CashSession current() {
        return getCurrentCashSession.run();
    }

    @GET
    @Path("/summary")
    @RolesAllowed({ AccountRole.ADMIN_ROLE, AccountRole.CASHIER_ROLE })
    public GetCashSessionSummaryResponse summary() {
        return getCashSessionSummary.run();
    }

}
