package com.gdgguadalajara.pos.productioncenter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.productioncenter.model.ProductionCenter;
import com.gdgguadalajara.pos.productioncenter.model.dto.ProductionCenterTicketItemsResponse;
import com.gdgguadalajara.pos.ticketItem.model.TicketItem;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@Path("/api/production-centers/{centerUuid}/items")
@AllArgsConstructor
public class ProductionCenterTicketItemsResource {

    private final GetCurrentSession getCurrentSession;

    @GET
    @RolesAllowed(AccountRole.PREPARER_ROLE)
    public List<ProductionCenterTicketItemsResponse> findActiveInStation(UUID centerUuid) {
        var center = ProductionCenter.findById(centerUuid);
        if (center == null)
            return List.of();
        var session = getCurrentSession.run();
        var response = session.productionCenters.stream()
                .filter(pc -> pc.id == centerUuid)
                .findFirst();
        if (response.isEmpty())
            return List.of();
        var items = TicketItem.<TicketItem>find(
                "SELECT ti FROM TicketItem ti WHERE ti.status IN ('ORDERED', 'PREPARING')\n"
                        + "AND EXISTS (\n"
                        + "  SELECT p FROM Product p\n"
                        + "  LEFT JOIN p.categories c\n"
                        + "  WHERE p.id = ti.originalProductId\n"
                        + "  AND (\n"
                        + "    p.productionCenter.id = ?1\n"
                        + "    OR (p.productionCenter IS NULL AND c.productionCenter.id = ?1)\n"
                        + "  )\n"
                        + ")\n"
                        + "ORDER BY ti.createdAt ASC",
                centerUuid).list();
        return items
                .stream()
                .map(item -> new ProductionCenterTicketItemsResponse(
                        item.ticket.id,
                        item.ticket.table != null ? item.ticket.table.id : null,
                        item.ticket.table != null ? item.ticket.table.name : null,
                        item.ticket.serviceType,
                        item))
                .collect(Collectors.toList());
    }
}
