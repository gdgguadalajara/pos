package com.gdgguadalajara.pos.floorplan.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

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
    public List<RestaurantTable> tables;
}