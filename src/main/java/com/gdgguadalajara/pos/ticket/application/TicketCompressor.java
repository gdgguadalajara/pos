package com.gdgguadalajara.pos.ticket.application;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;
import java.util.zip.Deflater;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import com.gdgguadalajara.pos.businessprofile.model.BusinessProfile;
import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.ticket.model.Ticket;
import com.gdgguadalajara.pos.ticket.model.dto.TicketCompressorResponse;
import com.gdgguadalajara.pos.ticket.model.dto.TicketCompressorTicketMapper;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TicketCompressor {

    private static final ObjectMapper cborMapper = new ObjectMapper(new CBORFactory());

    @ConfigProperty(name = "com.gdgguadalajara.pos.ticket_viewer")
    public String ticketViewer;

    public TicketCompressorResponse run(UUID ticketId) {
        var ticket = Ticket.<Ticket>findById(ticketId);
        if (ticket == null)
            throw DomainException.notFound("Ticket no encontrado");
        var businessProfile = BusinessProfile.<BusinessProfile>findAll().firstResult();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            var cborData = cborMapper.writeValueAsBytes(new TicketCompressorTicketMapper(businessProfile, ticket));
            var compresser = new Deflater(Deflater.BEST_COMPRESSION);
            compresser.setInput(cborData);
            compresser.finish();
            var buffer = new byte[1024];
            while (!compresser.finished())
                baos.write(buffer, 0, compresser.deflate(buffer));
            compresser.end();
            // TODO: migrate to Base45 QR?
            var url = ticketViewer + "?t=" + Base64.getEncoder().encodeToString(baos.toByteArray());
            return new TicketCompressorResponse(url);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw DomainException.badRequest("Error al comprimir el ticket");
        }
    }
}
