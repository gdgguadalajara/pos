package com.gdgguadalajara.pos.inventory;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.ingredient.model.Ingredient;
import com.gdgguadalajara.pos.inventory.application.AddStock;
import com.gdgguadalajara.pos.inventory.application.PerformInventoryAudit;
import com.gdgguadalajara.pos.inventory.model.InventoryAudit;
import com.gdgguadalajara.pos.inventory.model.dto.AddStockRequest;
import com.gdgguadalajara.pos.inventory.model.dto.CreateInventoryAuditRequest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;

@Path("/api/inventory")
@AllArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InventoryResource {

    private final AddStock addStock;
    private final PerformInventoryAudit performInventoryAudit;

    @POST
    @Path("/stock")
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public Ingredient addStock(@Valid AddStockRequest request) {
        return addStock.run(request);
    }

    @POST
    @Path("/audit")
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public InventoryAudit performInventoryAudit(@Valid CreateInventoryAuditRequest request) {
        return performInventoryAudit.run(request);
    }

}
