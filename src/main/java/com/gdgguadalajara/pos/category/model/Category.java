package com.gdgguadalajara.pos.category.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gdgguadalajara.pos.product.model.Product;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Category extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String description;

    @Column(nullable = false)
    public Boolean isEnabled;

    @Column(nullable = false)
    public LocalDate availableFrom;

    @Column(nullable = false)
    public LocalDate availableUntil;

    @Column(nullable = false)
    public LocalTime availableFromTime;

    @Column(nullable = false)
    public LocalTime availableUntilTime;

    @ElementCollection(targetClass = DayOfWeek.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "category_available_days", joinColumns = @JoinColumn(name = "category_id"))
    @Column(name = "day_of_week", nullable = false)
    public Set<DayOfWeek> availableDays;

    @JsonIgnore
    @ManyToMany()
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "category_product", joinColumns = { @JoinColumn(name = "category_id") }, inverseJoinColumns = {
            @JoinColumn(name = "product_id") })
    public Set<Product> products;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    public LocalDateTime updatedAt;
}
