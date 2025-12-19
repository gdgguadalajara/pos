package com.gdgguadalajara.pos.category;

import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.category.application.CreateCategory;
import com.gdgguadalajara.pos.category.application.DeleteCategory;
import com.gdgguadalajara.pos.category.application.UpdateCategory;
import com.gdgguadalajara.pos.category.model.Category;
import com.gdgguadalajara.pos.category.model.dto.CreateCategoryRequest;
import com.gdgguadalajara.pos.category.model.dto.UpdateCategoryRequest;
import com.gdgguadalajara.pos.common.model.PaginatedResponse;
import com.gdgguadalajara.pos.common.util.PanacheCriteria;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Path("/api/categories")
public class CategoryResource {

    private final CreateCategory createCategory;
    private final UpdateCategory updateCategory;
    private final DeleteCategory deleteCategory;
    private final GetCurrentSession getCurrentSession;

    @POST
    @Transactional
    @RolesAllowed({ AccountRole.ADMIN_ROLE })
    public Category create(CreateCategoryRequest request) {
        return createCategory.run(request);
    }

    @GET
    @Authenticated
    public PaginatedResponse<Category> read(
            @QueryParam("page") @DefaultValue("1") @Positive @Valid Integer page,
            @QueryParam("size") @DefaultValue("10") @Positive @Max(100) @Valid Integer size) {
        var sessionAccount = getCurrentSession.run();
        if (sessionAccount.role.equals(AccountRole.CASHIER)) {
            return PanacheCriteria.of(Category.class)
                    .eq("isEnabled", true)
                    .page(page, size)
                    .getResult();
        }
        return PanacheCriteria.of(Category.class)
                .page(page, size)
                .getResult();
    }

    @GET
    @Authenticated
    @Path("/{uuid}")
    public Category readById(UUID uuid) {
        var category = Category.<Category>findById(uuid);
        if (category == null)
            throw new NotFoundException("Categoria no encontrada");
        return category;
    }

    @PUT
    @Transactional
    @RolesAllowed({ AccountRole.ADMIN_ROLE })
    @Path("/{uuid}")
    public Category update(UUID uuid, UpdateCategoryRequest request) {
        return updateCategory.run(uuid, request);
    }

    @DELETE
    @Transactional
    @RolesAllowed({ AccountRole.ADMIN_ROLE })
    @Path("/{uuid}")
    public void delete(UUID uuid) {
        try {
            var category = Category.<Category>findById(uuid);
            deleteCategory.run(category);
        } catch (Exception e) {
        }
    }
}
