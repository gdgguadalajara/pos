package com.gdgguadalajara.pos.inventory.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gdgguadalajara.pos.ingredient.model.dto.IngredientSnapshot;
import com.gdgguadalajara.pos.product.model.Product;
import com.gdgguadalajara.pos.user.model.dto.UserSnapshot;

@Entity
public class StockOutEvent extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @ManyToOne
    public Product product;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json", nullable = false)
    @JsonIgnore
    public IngredientSnapshot ingredientSnapshot;

    @Column(nullable = false)
    public Double quantity;

    @Column(nullable = false)
    public Double currentStock;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json", nullable = false)
    @JsonIgnore
    public UserSnapshot userSnapshot;

    @CreationTimestamp
    public LocalDateTime date;
}
