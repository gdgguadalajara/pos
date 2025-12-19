package com.gdgguadalajara.pos.account.model;

public enum AccountStatus {
    PENDING_SETUP, // El usuario fue creado pero no ha puesto contraseña
    ACTIVE,        // Usuario normal operando
    LOCKED,        // Bloqueado por intentos fallidos
    DISABLED       // Deshabilitado por un admin (baja de empleado)
}