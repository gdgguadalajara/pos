package com.gdgguadalajara.pos.ticketItem.model;

public enum TicketItemStatus {
    ADDED,     // Agregado al carrito
    ORDERED,   // Enviado a cocina/preparación
    PREPARING, // En proceso
    READY,     // Listo para entrega
    DELIVERED, // Ya se entregó al cliente
    CANCELED   // Cancelado
}
