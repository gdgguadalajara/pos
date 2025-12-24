package com.gdgguadalajara.pos.product;

import java.util.List;
import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.category.model.Category;
import com.gdgguadalajara.pos.product.application.CreateProductCategory;
import com.gdgguadalajara.pos.product.application.RemoveProductCategory;
import com.gdgguadalajara.pos.product.model.Product;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@Path("/api/products/{productId}/categories")
@AllArgsConstructor
public class ProductCategoryResource {

    private final CreateProductCategory createProductCategory;
    private final RemoveProductCategory removeProductCategory;

    @GET
    @Authenticated
    public List<Category> readProductCategories(UUID productId) {
        return Category.list("select c from Category c join c.products p where p.id = ?1", productId);
    }

    @POST
    @Transactional
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    @Path("/{categoryId}")
    public Product create(UUID productId, UUID categoryId) {
        return createProductCategory.run(productId, categoryId);
    }

    @DELETE
    @Transactional
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    @Path("/{categoryId}")
    public void remove(UUID productId, UUID categoryId) {
        removeProductCategory.run(productId, categoryId);
    }

}
