package com.gdgguadalajara.pos.inventory.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gdgguadalajara.pos.ingredient.model.dto.IngredientSnapshot;
import com.gdgguadalajara.pos.user.model.dto.UserSnapshot;

@Entity
public class InventoryAudit extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    @JsonIgnore
    public UserSnapshot userSnapshot;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    @JsonIgnore
    public IngredientSnapshot ingredientSnapshot;

    @Column(nullable = false)
    public LocalDateTime date;

    @Column(nullable = false)
    public Double physicalStock;

    @Column(nullable = false)
    public Double systemStockAtMoment;

    @Column(nullable = false)
    public Double shrinkage;
}
