package com.gdgguadalajara.pos.auth;

import com.gdgguadalajara.pos.auth.application.Authenticate;
import com.gdgguadalajara.pos.auth.application.GetCurrentSession;
import com.gdgguadalajara.pos.auth.model.dto.AuthenticateRequest;
import com.gdgguadalajara.pos.auth.model.dto.AuthenticateResponse;
import com.gdgguadalajara.pos.user.model.User;

import io.quarkus.security.Authenticated;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Path("/api/auth")
public class AuthResource {

    private final Authenticate authenticate;
    private final GetCurrentSession getCurrentSession;

    @POST
    public AuthenticateResponse auth(@Valid AuthenticateRequest request) {
        return authenticate.run(request);
    }

    @GET
    @Authenticated
    public User whoami() {
        return getCurrentSession.run().user;
    }
}
