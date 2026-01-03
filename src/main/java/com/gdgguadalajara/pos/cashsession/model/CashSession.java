package com.gdgguadalajara.pos.cashsession.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.gdgguadalajara.pos.user.model.User;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CashSession extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @ManyToOne
    @JoinColumn(name = "opened_by_id")
    public User openedBy;

    @ManyToOne
    @JoinColumn(name = "closed_by_id")
    public User closedBy;

    @Column(nullable = false)
    public LocalDateTime openingDate;
    public LocalDateTime closingDate;

    @Column(nullable = false)
    public BigDecimal initialBalance;

    public BigDecimal totalSales;
    public BigDecimal totalExpenses;
    public BigDecimal reportedBalance;
    public BigDecimal difference;

    @Enumerated(EnumType.STRING)
    public CashSessionStatus status;

    @Column(columnDefinition = "TEXT")
    public String note;
}
