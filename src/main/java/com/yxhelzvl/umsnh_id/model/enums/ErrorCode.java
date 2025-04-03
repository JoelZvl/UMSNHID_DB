package com.yxhelzvl.umsnh_id.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATE_MATRICULA("DUPLICATE_MATRICULA", "La matrícula ya está registrada"),
    DUPLICATE_EMAIL("DUPLICATE_EMAIL", "El correo electrónico ya está en uso"),
    SERVER_ERROR("SRV-500", "Error interno del servidor"),
    USER_NOT_FOUND("USER_NO_FOUND", "Usuario no encontrado"),
    INVALID_CREDENTIALS("INVALID_CREDENTIALS", "Credenciales inválidas");

    private final String code;
    private final String message;

    // Constructor y getters
}