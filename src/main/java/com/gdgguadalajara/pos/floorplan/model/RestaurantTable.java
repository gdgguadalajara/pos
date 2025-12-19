package com.gdgguadalajara.pos.floorplan.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class RestaurantTable extends PanacheEntityBase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public Integer posX;

    @Column(nullable = false)
    public Integer posY;

    public Integer width = 1;
    public Integer height = 1;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public RestaurantTableStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id")
    public Floor floor;
}