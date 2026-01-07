package com.gdgguadalajara.pos.account.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gdgguadalajara.pos.productioncenter.model.ProductionCenter;
import com.gdgguadalajara.pos.user.model.User;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
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
import jakarta.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Account extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(unique = true, nullable = false)
    public String username;

    @JsonIgnore
    public String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public AccountStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public AccountRole role;

    @JsonBackReference
    @OneToOne
    public User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_production_center", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "production_center_id"))
    public Set<ProductionCenter> productionCenters = new HashSet<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    public LocalDateTime updatedAt;
}
