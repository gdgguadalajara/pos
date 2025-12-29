package com.gdgguadalajara.pos.floorplan.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Floor extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public Integer levelOrder;

    @Column(nullable = false)
    public Integer gridWidth;

    @Column(nullable = false)
    public Integer gridHeight;

    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    public List<RestaurantTable> tables;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    public LocalDateTime updatedAt;
}