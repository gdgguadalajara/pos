package com.gdgguadalajara.pos.businessprofile.model;

import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BusinessProfile extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(nullable = false)
    public String name;
    
    @Column(nullable = false)
    public String legalName;
    
    public String taxId;

    @Column(length = 500)
    public String address;

    public String phone;
    public String email;
    public String website;

    @Column(length = 1000)
    public String ticketFooter;

    public String logoUrl;
}
