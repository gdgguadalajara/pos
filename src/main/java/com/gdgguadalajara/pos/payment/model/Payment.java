package com.gdgguadalajara.pos.payment.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import com.gdgguadalajara.pos.cashsession.model.CashSession;
import com.gdgguadalajara.pos.ticket.model.Ticket;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Payment extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ticket_id", nullable = false)
    public Ticket ticket;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cash_session_id", nullable = false)
    public CashSession cashSession;

    @Column(nullable = false)
    public BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public PaymentMethod method;

    public String externalReference;

    public BigDecimal changeGiven = BigDecimal.ZERO;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    public LocalDateTime createdAt;
}
