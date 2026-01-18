package com.gdgguadalajara.pos.product;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.common.model.PaginatedResponse;
import com.gdgguadalajara.pos.common.model.dto.PaginationRequestParams;
import com.gdgguadalajara.pos.common.util.PanacheCriteria;
import com.gdgguadalajara.pos.product.application.CreateProduct;
import com.gdgguadalajara.pos.product.application.DeleteProduct;
import com.gdgguadalajara.pos.product.application.UpdateProduct;
import com.gdgguadalajara.pos.product.model.Product;
import com.gdgguadalajara.pos.product.model.dto.CreateProductRequest;
import com.gdgguadalajara.pos.product.model.dto.ReadProductsFilter;
import com.gdgguadalajara.pos.product.model.dto.UpdateProductRequest;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Path("/api/products")
public class ProductResource {

    private final CreateProduct createProduct;
    private final UpdateProduct updateProduct;
    private final DeleteProduct deleteProduct;

    @POST
    @Transactional
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public Product create(@Valid CreateProductRequest request) {
        return createProduct.run(request);
    }

    @GET
    @Authenticated
    public PaginatedResponse<Product> read(@BeanParam @Valid ReadProductsFilter filter) {
        var criteria = PanacheCriteria.of(Product.class)
                .eq("isEnabled", filter.isEnabled)
                .eq("id", filter.id)
                .like("name", filter.name)
                .orderBy(filter.sort)
                .page(filter.page, filter.size);
        if (filter.availableDays != null)
            for (DayOfWeek day : filter.availableDays)
                criteria.memberOf(day, "availableDays");
        return criteria.getResult();
    }

    @GET
    @Authenticated
    @Path("/availables")
    public PaginatedResponse<Product> readAvailables(@BeanParam @Valid PaginationRequestParams params) {
        var currentDate = LocalDate.now();
        var currentTime = LocalTime.now();
        return PanacheCriteria.of(Product.class)
                .eq("isEnabled", true)
                .le("availableFrom", currentDate)
                .ge("availableUntil", currentDate)
                .le("availableFromTime", currentTime)
                .ge("availableUntilTime", currentTime)
                .memberOf(currentDate.getDayOfWeek(), "availableDays")
                .page(params.page, params.size).getResult();
    }

    @GET
    @Authenticated
    @Path("/{uuid}")
    public Product readById(UUID uuid) {
        var product = Product.<Product>findById(uuid);
        if (product == null)
            throw DomainException.notFound("Product no encontrado");
        return product;
    }

    @PUT
    @Transactional
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    @Path("/{uuid}")
    public Product update(UUID uuid, UpdateProductRequest request) {
        return updateProduct.run(uuid, request);
    }

    @DELETE
    @Transactional
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    @Path("/{uuid}")
    public void delete(UUID uuid) {
        try {
            deleteProduct.run(Product.<Product>findById(uuid));
        } catch (Exception e) {
        }
    }

}
