package com.gdgguadalajara.pos.ticket.model.dto;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gdgguadalajara.pos.businessprofile.model.BusinessProfile;
import com.gdgguadalajara.pos.ticket.model.Ticket;

public class TicketCompressorTicketMapper {

    @JsonProperty("b")
    String businessName;

    @JsonProperty("t")
    String ticketId;

    @JsonProperty("w")
    String waiterId;

    @JsonProperty("ti")
    String tableId;

    @JsonProperty("i")
    List<TicketCompressorTicketItemMapper> items;

    @JsonProperty("tt")
    BigDecimal total;

    @JsonProperty("c")
    Long createdAt;

    public TicketCompressorTicketMapper(BusinessProfile businessProfile, Ticket ticket) {
        this.businessName = businessProfile.name;
        this.ticketId = ticket.id.toString();
        this.waiterId = ticket.owner.id.toString();
        this.tableId = ticket.table != null ? ticket.table.id.toString() : "";
        this.total = ticket.totalAmount;
        this.createdAt = ticket.createdAt.atZone(ZoneId.systemDefault()).toEpochSecond();
        Map<UUID, TicketCompressorTicketItemMapper> items = new HashMap<>();
        ticket.items.forEach(item -> {
            var key = item.originalProductId;
            if (!items.containsKey(key))
                items.put(key, new TicketCompressorTicketItemMapper(0, item.productName, BigDecimal.ZERO));
            var itemMapper = items.get(key);
            items.put(key,
                    new TicketCompressorTicketItemMapper(
                            itemMapper.quantity + 1,
                            item.productName,
                            itemMapper.price.add(item.unitPrice)));
        });
        this.items = items.values().stream().toList();
    }

    public record TicketCompressorTicketItemMapper(
            @JsonProperty("q") Integer quantity,
            @JsonProperty("n") String name,
            @JsonProperty("p") BigDecimal price) {
    }
}
