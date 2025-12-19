package com.gdgguadalajara.pos.invitation.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import com.gdgguadalajara.pos.user.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Invitation extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(unique = true, nullable = false)
    public String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    @Column(nullable = false)
    public LocalDateTime expiresAt;

    @Column(nullable = true)
    public LocalDateTime usedAt;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    public LocalDateTime createdAt;

    public boolean isValid() {
        return usedAt == null && LocalDateTime.now().isBefore(expiresAt);
    }
}