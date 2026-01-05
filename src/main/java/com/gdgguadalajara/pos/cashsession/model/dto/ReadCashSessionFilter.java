package com.gdgguadalajara.pos.cashsession.model.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.gdgguadalajara.pos.common.model.dto.PaginationRequestParams;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public class ReadCashSessionFilter extends PaginationRequestParams {

    @QueryParam("id")
    public UUID id;

    @QueryParam("openedBy")
    public String openedBy;

    @QueryParam("closedBy")
    public String closedBy;

    @QueryParam("openingDate")
    public LocalDate openingDate;

    @QueryParam("closingDate")
    public LocalDate closingDate;

    @QueryParam("negatives")
    @DefaultValue("false")
    public Boolean negatives;
}
