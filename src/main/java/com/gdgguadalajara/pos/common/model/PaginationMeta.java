package com.gdgguadalajara.pos.common.model;

public record PaginationMeta(
        long totalRecords,
        int currentPage,
        int totalPages,
        Integer nextPage,
        Integer prevPage) {
}
