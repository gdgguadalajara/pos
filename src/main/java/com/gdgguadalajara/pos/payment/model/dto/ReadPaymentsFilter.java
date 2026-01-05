package com.gdgguadalajara.pos.payment.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.gdgguadalajara.pos.common.model.dto.PaginationRequestParams;
import com.gdgguadalajara.pos.payment.model.PaymentMethod;

import jakarta.ws.rs.QueryParam;

public class ReadPaymentsFilter extends PaginationRequestParams {

    @QueryParam("id")
    public UUID id;

    @QueryParam("ticketId")
    public UUID ticketId;

    @QueryParam("cashSessionId")
    public UUID cashSessionId;

    @QueryParam("amount")
    public BigDecimal amount;

    @QueryParam("method")
    public PaymentMethod method;

    @QueryParam("externalReference")
    public String externalReference;

    @QueryParam("createdAt")
    public LocalDate createdAt;
}
