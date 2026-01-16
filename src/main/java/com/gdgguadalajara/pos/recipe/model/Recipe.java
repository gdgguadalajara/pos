package com.gdgguadalajara.pos.recipe.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gdgguadalajara.pos.ingredient.model.Ingredient;
import com.gdgguadalajara.pos.product.model.Product;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Recipe extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String description;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(nullable = false)
    public Product product;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ingredient_recipe", joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    @JsonIgnore
    public Set<Ingredient> ingredients = new java.util.HashSet<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    public LocalDateTime updatedAt;
}
