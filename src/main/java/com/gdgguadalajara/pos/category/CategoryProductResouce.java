package com.gdgguadalajara.pos.category;

import java.util.List;
import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.category.application.CreateCategoryProduct;
import com.gdgguadalajara.pos.category.application.RemoveCategoryProduct;
import com.gdgguadalajara.pos.category.model.Category;
import com.gdgguadalajara.pos.product.model.Product;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
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

    @POST
    @Transactional
    @RolesAllowed({ AccountRole.ADMIN_ROLE })
    @Path("/{productId}")
    public Category create(UUID categoryId, UUID productId) {
        return createCategoryProduct.run(categoryId, productId);
    }

    @DELETE
    @Transactional
    @RolesAllowed({ AccountRole.ADMIN_ROLE })
    @Path("/{productId}")
    public void delete(UUID categoryId, UUID productId) {
        removeCategoryProduct.run(categoryId, productId);
    }

}
