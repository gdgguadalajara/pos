package com.gdgguadalajara.pos.productioncenter;

import java.util.UUID;

import org.jboss.resteasy.reactive.RestStreamElementType;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.common.PageBuilder;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.common.model.PaginatedResponse;
import com.gdgguadalajara.pos.common.model.dto.PaginationRequestParams;
import com.gdgguadalajara.pos.productioncenter.application.CreateProductionCenter;
import com.gdgguadalajara.pos.productioncenter.application.DeleteProduncionCenter;
import com.gdgguadalajara.pos.productioncenter.application.ServerSideProductionCenterEvents;
import com.gdgguadalajara.pos.productioncenter.model.ProductionCenter;
import com.gdgguadalajara.pos.productioncenter.model.dto.CreateProductionCenterRequest;

import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Multi;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;

@Path("/api/production-centers")
@AllArgsConstructor
public class ProductionCenterResource {

    private final CreateProductionCenter createProductionCenter;
    private final DeleteProduncionCenter deleteProduncionCenter;
    private final ServerSideProductionCenterEvents serverSideProductionCenterEvents;

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

    @GET
    @Path("/{uuid}")
    @Authenticated
    public ProductionCenter read(UUID uuid) {
        var center = ProductionCenter.<ProductionCenter>findById(uuid);
        if (center == null)
            throw DomainException.notFound("Centro de producción no encontrado.");
        return ProductionCenter.findById(uuid);
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

    @GET
    @Path("/{uuid}/stream")
    // TODO: add security
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestStreamElementType(MediaType.APPLICATION_JSON)
    public Multi<ProductionCenter> stream(UUID uuid) {
        return serverSideProductionCenterEvents.getEventStream()
                .filter(event -> uuid.equals(event.id));
    }
}
