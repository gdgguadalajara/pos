package com.gdgguadalajara.pos.account;

import com.gdgguadalajara.pos.account.application.ActivateAccount;
import com.gdgguadalajara.pos.account.model.Account;
import com.gdgguadalajara.pos.account.model.dto.ActivateAccountRequest;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Path("/api/accounts")
public class AccountResource {

    private final ActivateAccount activateAccount;

    @POST
    @Path("/activate")
    @Transactional
    public Account activateAccount(ActivateAccountRequest request) {
        return activateAccount.run(request);
    }
}
