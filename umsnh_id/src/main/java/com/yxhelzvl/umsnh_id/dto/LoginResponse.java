package com.yxhelzvl.umsnh_id.dto;

import com.yxhelzvl.umsnh_id.model.Usuario;
import lombok.Getter;

@Getter
public class LoginResponse {
    private String token;
    private Usuario usuario;

    // Constructor
    public LoginResponse(String token, Usuario usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    // Getters
}