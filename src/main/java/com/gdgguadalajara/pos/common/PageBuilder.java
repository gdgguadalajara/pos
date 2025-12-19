package com.gdgguadalajara.pos.common;

import com.gdgguadalajara.pos.common.model.PaginatedResponse;
import com.gdgguadalajara.pos.common.model.PaginationMeta;

import io.quarkus.hibernate.orm.panache.PanacheQuery;

public class PageBuilder {

    public static <T> PaginatedResponse<T> of(PanacheQuery<T> query, int pageIndex, int pageSize) {
        int panacheIndex = (pageIndex > 0) ? pageIndex - 1 : 0;

        var paginatedQuery = query.page(panacheIndex, pageSize);
        var list = paginatedQuery.list();
        var totalRecords = paginatedQuery.count();

        int totalPages = (totalRecords == 0) ? 0 : (int) Math.ceil((double) totalRecords / pageSize);

        Integer nextPage = (pageIndex < totalPages) ? pageIndex + 1 : null;
        Integer prevPage = (pageIndex > 1) ? pageIndex - 1 : null;

        var meta = new PaginationMeta(
                totalRecords,
                panacheIndex + 1,
                totalPages,
                nextPage,
                prevPage);

        return new PaginatedResponse<>(list, meta);
    }
}