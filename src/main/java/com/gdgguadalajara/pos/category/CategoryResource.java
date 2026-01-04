package com.gdgguadalajara.pos.category;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.category.application.CreateCategory;
import com.gdgguadalajara.pos.category.application.DeleteCategory;
import com.gdgguadalajara.pos.category.application.UpdateCategory;
import com.gdgguadalajara.pos.category.model.Category;
import com.gdgguadalajara.pos.category.model.dto.CreateCategoryRequest;
import com.gdgguadalajara.pos.category.model.dto.ReadCategoriesFilter;
import com.gdgguadalajara.pos.category.model.dto.UpdateCategoryRequest;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.common.model.PaginatedResponse;
import com.gdgguadalajara.pos.common.util.PanacheCriteria;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
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

    @POST
    @Transactional
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    public Category create(CreateCategoryRequest request) {
        return createCategory.run(request);
    }

    @GET
    @Authenticated
    public PaginatedResponse<Category> read(@BeanParam @Valid ReadCategoriesFilter filter) {
        var criteria = PanacheCriteria.of(Category.class)
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
    public PaginatedResponse<Category> readAvailables(
            @QueryParam("page") @DefaultValue("1") @Positive @Valid Integer page,
            @QueryParam("size") @DefaultValue("10") @Positive @Max(100) @Valid Integer size) {
        var currentDate = LocalDate.now();
        var currentTime = LocalTime.now();
        return PanacheCriteria.of(Category.class)
                .eq("isEnabled", true)
                .le("availableFrom", currentDate)
                .ge("availableUntil", currentDate)
                .le("availableFromTime", currentTime)
                .ge("availableUntilTime", currentTime)
                .memberOf(currentDate.getDayOfWeek(), "availableDays")
                .page(page, size).getResult();
    }

    @GET
    @Authenticated
    @Path("/{uuid}")
    public Category readById(UUID uuid) {
        var category = Category.<Category>findById(uuid);
        if (category == null)
            throw DomainException.notFound("Categoria no encontrada");
        return category;
    }

    @PUT
    @Transactional
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    @Path("/{uuid}")
    public Category update(UUID uuid, UpdateCategoryRequest request) {
        return updateCategory.run(uuid, request);
    }

    @DELETE
    @Transactional
    @RolesAllowed(AccountRole.ADMIN_ROLE)
    @Path("/{uuid}")
    public void delete(UUID uuid) {
        try {
            var category = Category.<Category>findById(uuid);
            deleteCategory.run(category);
        } catch (Exception e) {
        }
    }
}
