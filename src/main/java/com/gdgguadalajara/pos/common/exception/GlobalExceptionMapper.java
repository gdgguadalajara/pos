package com.gdgguadalajara.pos.common.exception;

import com.gdgguadalajara.pos.common.model.ErrorResponse;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(Exception e) {
        if (!uriInfo.getPath().startsWith("/api")) {
            return null;
        }
        int status = (e instanceof WebApplicationException wae)
                ? wae.getResponse().getStatus()
                : 500;

        ErrorResponse error = new ErrorResponse(
                e.getMessage() != null ? e.getMessage() : "Error interno del servidor",
                status,
                e.getClass().getSimpleName());

        return Response.status(status)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}