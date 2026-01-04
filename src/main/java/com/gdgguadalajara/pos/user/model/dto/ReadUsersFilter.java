package com.gdgguadalajara.pos.user.model.dto;

import java.util.UUID;

import com.gdgguadalajara.pos.account.model.AccountRole;
import com.gdgguadalajara.pos.account.model.AccountStatus;
import com.gdgguadalajara.pos.common.model.dto.PaginationRequestParams;

import jakarta.ws.rs.QueryParam;

public class ReadUsersFilter extends PaginationRequestParams {

    @QueryParam("id")
    public UUID id;

    @QueryParam("name")
    public String name;

    @QueryParam("email")
    public String email;

    @QueryParam("username")
    public String username;

    @QueryParam("status")
    public AccountStatus status;

    @QueryParam("role")
    public AccountRole role;
}
