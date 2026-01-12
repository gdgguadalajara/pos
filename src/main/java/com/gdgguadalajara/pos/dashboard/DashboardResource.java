package com.gdgguadalajara.pos.dashboard;

import java.time.LocalDateTime;
import java.util.List;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.dashboard.model.dto.AverageOrderValueRequest;
import com.gdgguadalajara.pos.dashboard.model.dto.AverageOrderValueResponse;
import com.gdgguadalajara.pos.dashboard.model.dto.ProductPerformanceRequest;
import com.gdgguadalajara.pos.dashboard.model.dto.ProductPerformanceResponse;
import com.gdgguadalajara.pos.ticket.model.Ticket;
import com.gdgguadalajara.pos.ticketItem.model.TicketItem;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api/dashboard")
public class DashboardResource {

    @GET
    @Path("/average-order-value/single")
    @RolesAllowed({ AccountRole.ADMIN_ROLE })
    public AverageOrderValueResponse readSingle(@BeanParam @Valid AverageOrderValueRequest request) {
        if (request.start == null)
            request.start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        if (request.end == null)
            request.end = LocalDateTime.now();
        return Ticket.getEntityManager()
                .createQuery("SELECT "
                        + "CAST(NOW() AS java.time.LocalDate) as createdDate, "
                        + "SUM(t.totalAmount), "
                        + "COUNT(t.id), "
                        + "AVG(t.totalAmount) "
                        + "FROM Ticket t "
                        + "WHERE t.createdAt BETWEEN :start AND :end "
                        + "AND t.status != 'CANCELED'",
                        AverageOrderValueResponse.class)
                .setParameter("start", request.start)
                .setParameter("end", request.end)
                .getSingleResult();
    }

    @GET
    @Path("/average-order-value")
    @RolesAllowed({ AccountRole.ADMIN_ROLE })
    public List<AverageOrderValueResponse> read(@BeanParam @Valid AverageOrderValueRequest request) {
        if (request.start == null)
            request.start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        if (request.end == null)
            request.end = LocalDateTime.now();
        return Ticket.getEntityManager()
                .createQuery(
                        "SELECT "
                                + "CAST(t.createdAt AS java.time.LocalDate), "
                                + "SUM(t.totalAmount), "
                                + "COUNT(t.id), "
                                + "AVG(t.totalAmount) "
                                + "FROM Ticket t "
                                + "WHERE t.createdAt BETWEEN :start AND :end "
                                + "AND t.status != 'CANCELED' "
                                + "GROUP BY CAST(t.createdAt as DATE) ",
                        AverageOrderValueResponse.class)
                .setParameter("start", request.start)
                .setParameter("end", request.end)
                .getResultList();
    }

    @GET
    @Path("/product-performance")
    @RolesAllowed({ AccountRole.ADMIN_ROLE })
    public List<ProductPerformanceResponse> read(@BeanParam @Valid ProductPerformanceRequest request) {
        if (request.start == null)
            request.start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        if (request.end == null)
            request.end = LocalDateTime.now();
        return TicketItem.find(
                "SELECT "
                        + "ti.productName, "
                        + "COUNT(ti.id), "
                        + "SUM(ti.unitPrice) "
                        + "FROM TicketItem ti "
                        + "WHERE ti.createdAt BETWEEN ?1 AND ?2 "
                        + "AND ti.status != 'CANCELED' "
                        + "GROUP BY ti.productName "
                        + "ORDER BY COUNT(ti.id) "
                        + (request.isAscending ? "ASC" : "DESC"),
                request.start, request.end)
                .range(0, request.limit)
                .project(ProductPerformanceResponse.class)
                .list();
    }
}
