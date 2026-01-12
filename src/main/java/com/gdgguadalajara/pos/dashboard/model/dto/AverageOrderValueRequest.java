package com.gdgguadalajara.pos.dashboard.model.dto;

import java.time.LocalDateTime;
import jakarta.ws.rs.QueryParam;

public class AverageOrderValueRequest {

    @QueryParam("start")
    public LocalDateTime start;

    @QueryParam("end")
    public LocalDateTime end;
}
