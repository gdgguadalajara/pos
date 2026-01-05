package com.gdgguadalajara.pos.common.util;

import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import com.gdgguadalajara.pos.common.model.PaginatedResponse;
import com.gdgguadalajara.pos.common.model.PaginationMeta;

public class PanacheCriteria<T extends PanacheEntityBase> {

    private final Class<T> entityClass;
    private final StringJoiner whereClause = new StringJoiner(" AND ");
    private final List<Object> params = new ArrayList<>();
    private String orderBy = "";

    private int pageIndex = 1;
    private int pageSize = 20;

    private PanacheCriteria(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public static <T extends PanacheEntityBase> PanacheCriteria<T> of(Class<T> entityClass) {
        return new PanacheCriteria<>(entityClass);
    }

    public PanacheCriteria<T> isNull(String field) {
        whereClause.add(field + " IS NULL");
        return this;
    }

    public PanacheCriteria<T> isNotNull(String field) {
        whereClause.add(field + " IS NOT NULL");
        return this;
    }

    public PanacheCriteria<T> eq(String field, Object value) {
        if (value != null) {
            addParam(field + " = ?", value);
        }
        return this;
    }

    public PanacheCriteria<T> ne(String field, Object value) {
        if (value != null) {
            addParam(field + " <> ?", value);
        }
        return this;
    }

    public PanacheCriteria<T> gt(String field, Object value) {
        if (value != null) {
            addParam(field + " > ?", value);
        }
        return this;
    }

    public PanacheCriteria<T> ge(String field, Object value) {
        if (value != null) {
            addParam(field + " >= ?", value);
        }
        return this;
    }

    public PanacheCriteria<T> lt(String field, Object value) {
        if (value != null) {
            addParam(field + " < ?", value);
        }
        return this;
    }

    public PanacheCriteria<T> le(String field, Object value) {
        if (value != null) {
            addParam(field + " <= ?", value);
        }
        return this;
    }

    public PanacheCriteria<T> between(String field, Object min, Object max) {
        if (min != null && max != null) {
            int indexMin = params.size() + 1;
            params.add(min);
            int indexMax = params.size() + 1;
            params.add(max);
            whereClause.add(field + " BETWEEN ?" + indexMin + " AND ?" + indexMax);
        }
        return this;
    }

    public PanacheCriteria<T> like(String field, String value) {
        if (value != null && !value.isBlank()) {
            addParam("LOWER(" + field + ") LIKE ?", "%" + value.toLowerCase() + "%");
        }
        return this;
    }

    public PanacheCriteria<T> in(String field, List<?> values) {
        if (values != null && !values.isEmpty()) {
            StringBuilder inClause = new StringBuilder(field + " IN (");
            for (int i = 0; i < values.size(); i++) {
                int index = params.size() + 1;
                inClause.append("?").append(index);
                params.add(values.get(i));
                if (i < values.size() - 1) {
                    inClause.append(", ");
                }
            }
            inClause.append(")");
            whereClause.add(inClause.toString());
        }
        return this;
    }

    public PanacheCriteria<T> memberOf(Object value, String collectionField) {
        if (value != null) {
            int index = params.size() + 1;
            whereClause.add("?" + index + " MEMBER OF e." + collectionField);
            params.add(value);
        }
        return this;
    }

    private void addParam(String clause, Object value) {
        int index = params.size() + 1;
        whereClause.add(clause.replace("?", "?" + index));
        params.add(value);
    }

    public PanacheCriteria<T> orderBy(String orderBy) {
        if (orderBy.contains(",")) {
            String[] parts = orderBy.split(",");
            orderBy = parts[0] + " " + parts[1].toUpperCase();
        }
        this.orderBy = orderBy;
        return this;
    }

    public PanacheCriteria<T> page(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        return this;
    }

    public PaginatedResponse<T> getResult() {
        EntityManager em = Panache.getEntityManager();

        String hqlWhere = whereClause.length() > 0 ? " WHERE " + whereClause.toString() : "";
        String hqlOrder = !orderBy.isEmpty() ? " ORDER BY " + orderBy : "";

        String selectHql = "SELECT e FROM " + entityClass.getSimpleName() + " e" + hqlWhere + hqlOrder;
        Query dataQuery = em.createQuery(selectHql, entityClass);

        String countHql = "SELECT count(e) FROM " + entityClass.getSimpleName() + " e" + hqlWhere;
        Query countQuery = em.createQuery(countHql);

        for (int i = 0; i < params.size(); i++) {
            dataQuery.setParameter(i + 1, params.get(i));
            countQuery.setParameter(i + 1, params.get(i));
        }

        long totalRecords = (long) countQuery.getSingleResult();

        int panacheIndex = (pageIndex > 0) ? pageIndex - 1 : 0;
        dataQuery.setFirstResult(panacheIndex * pageSize);
        dataQuery.setMaxResults(pageSize);

        List<T> list = dataQuery.getResultList();

        int totalPages = (totalRecords == 0) ? 0 : (int) Math.ceil((double) totalRecords / pageSize);
        Integer nextPage = (pageIndex < totalPages) ? pageIndex + 1 : null;
        Integer prevPage = (pageIndex > 1) ? pageIndex - 1 : null;

        PaginationMeta meta = new PaginationMeta(
                totalRecords,
                panacheIndex + 1,
                totalPages,
                nextPage,
                prevPage);

        return new PaginatedResponse<>(list, meta);
    }

    public Optional<T> firstResult() {
        EntityManager em = Panache.getEntityManager();

        String hqlWhere = whereClause.length() > 0 ? " WHERE " + whereClause.toString() : "";
        String hqlOrder = !orderBy.isEmpty() ? " ORDER BY " + orderBy : "";

        String selectHql = "SELECT e FROM " + entityClass.getSimpleName() + " e" + hqlWhere + hqlOrder;
        Query query = em.createQuery(selectHql, entityClass);

        for (int i = 0; i < params.size(); i++) {
            query.setParameter(i + 1, params.get(i));
        }

        query.setMaxResults(1);
        List<T> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}