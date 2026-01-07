package com.gdgguadalajara.pos.productioncenter;

import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.common.PageBuilder;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.common.model.PaginatedResponse;
import com.gdgguadalajara.pos.common.model.dto.PaginationRequestParams;
import com.gdgguadalajara.pos.productioncenter.application.CreateProductionCenter;
import com.gdgguadalajara.pos.productioncenter.application.DeleteProduncionCenter;
import com.gdgguadalajara.pos.productioncenter.model.ProductionCenter;
import com.gdgguadalajara.pos.productioncenter.model.dto.CreateProductionCenterRequest;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@Path("/api/production-centers")
@AllArgsConstructor
public class ProductionCenterResource {

    private final CreateProductionCenter createProductionCenter;
    private final DeleteProduncionCenter deleteProduncionCenter;

    @POST
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    @Transactional
    public ProductionCenter create(@Valid CreateProductionCenterRequest request) {
        return createProductionCenter.run(request);
    }

    @GET
    @Authenticated
    public PaginatedResponse<ProductionCenter> findAll(@BeanParam @Valid PaginationRequestParams params) {
        return PageBuilder.of(ProductionCenter.findAll(), params.page, params.size);
    }

    @DELETE
    @Path("/{uuid}")
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    @Transactional
    public void delete(UUID uuid) {
        var productionCenter = ProductionCenter.<ProductionCenter>findById(uuid);
        if (productionCenter == null)
            throw DomainException.notFound("Centro de producción no encontrado.");
        deleteProduncionCenter.run(productionCenter);
    }
}
