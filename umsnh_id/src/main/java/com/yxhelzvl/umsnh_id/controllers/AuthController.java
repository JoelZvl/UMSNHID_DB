package com.yxhelzvl.umsnh_id.controllers;

import com.yxhelzvl.umsnh_id.dto.ApiResponse;
import com.yxhelzvl.umsnh_id.dto.LoginRequest;
import com.yxhelzvl.umsnh_id.dto.LoginResponse;
import com.yxhelzvl.umsnh_id.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth") // Asegúrate que la ruta base sea correcta
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login") // La ruta completa será /api/auth/login
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.authenticate(request);
        return ResponseEntity.ok(ApiResponse.success("Autenticación exitosa", loginResponse));
    }
}
