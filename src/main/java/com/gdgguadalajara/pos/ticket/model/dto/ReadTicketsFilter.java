package com.gdgguadalajara.pos.ticket.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.gdgguadalajara.pos.common.model.dto.PaginationRequestParams;
import com.gdgguadalajara.pos.ticket.model.TicketStatus;

import jakarta.ws.rs.QueryParam;

public class ReadTicketsFilter extends PaginationRequestParams {

    @QueryParam("id")
    public UUID id;

    @QueryParam("owner")
    public String owner;

    @QueryParam("amount")
    public BigDecimal amount;

    @QueryParam("status")
    public TicketStatus status;

    @QueryParam("createdAt")
    public LocalDate createdAt;
}
