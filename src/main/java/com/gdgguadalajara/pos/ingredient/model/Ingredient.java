package com.gdgguadalajara.pos.ingredient.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.EnumType;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gdgguadalajara.pos.recipe.model.Recipe;

@Entity
public class Ingredient extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(nullable = false)
    public String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public IngredientUnitType purchaseUnit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public IngredientUnitType usageUnit;

    @Column(nullable = false)
    public Double systemStock;

    @Column(nullable = false)
    public Double costPerPurchaseUnit;

    @ManyToMany(mappedBy = "ingredients")
    @JsonIgnore
    public Set<Recipe> recipes;

    static public Double getUsageToPurchaseFactor(Ingredient ingredient) {
        return IngredientUnitType.getConversionFactor(ingredient.usageUnit, ingredient.purchaseUnit);
    }
}
