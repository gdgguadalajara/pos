package com.gdgguadalajara.pos.category;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.category.application.CreateCategoryProduct;
import com.gdgguadalajara.pos.category.application.RemoveCategoryProduct;
import com.gdgguadalajara.pos.category.model.Category;
import com.gdgguadalajara.pos.common.PageBuilder;
import com.gdgguadalajara.pos.common.model.PaginatedResponse;
import com.gdgguadalajara.pos.product.model.Product;

import io.quarkus.panache.common.Parameters;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;

@Path("/api/categories/{categoryId}/products")
@AllArgsConstructor
public class CategoryProductResouce {

    private final CreateCategoryProduct createCategoryProduct;
    private final RemoveCategoryProduct removeCategoryProduct;

    @GET
    @Authenticated
    public List<Product> getCategoryProducts(UUID categoryId) {
        return Product.list("select p from Product p join p.categories c where c.id = ?1", categoryId);
    }

    @GET
    @Authenticated
    @Path("/availables")
    public PaginatedResponse<Product> readAvailables(
            UUID categoryId,
            @QueryParam("page") @DefaultValue("1") @Positive @Valid Integer page,
            @QueryParam("size") @DefaultValue("10") @Positive @Max(100) @Valid Integer size) {
        var queryString = new StringJoiner(" and ")
                .add("SELECT p FROM Product p JOIN p.categories c WHERE c.id = :categoryId")
                .add("p.isEnabled = true")
                .add(":today BETWEEN p.availableFrom AND p.availableUntil")
                .add(":now BETWEEN p.availableFromTime AND p.availableUntilTime")
                .add(":dayOfWeek MEMBER OF p.availableDays")
                .toString();
        var query = Product.<Product>find(queryString,
                Parameters.with("categoryId", categoryId)
                        .and("today", LocalDate.now())
                        .and("now", LocalTime.now())
                        .and("dayOfWeek", LocalDate.now().getDayOfWeek()));
        return PageBuilder.of(query, page, size);
    }

    @POST
    @Transactional
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    @Path("/{productId}")
    public Category create(UUID categoryId, UUID productId) {
        return createCategoryProduct.run(categoryId, productId);
    }

    @DELETE
    @Transactional
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    @Path("/{productId}")
    public void delete(UUID categoryId, UUID productId) {
        removeCategoryProduct.run(categoryId, productId);
    }

}
