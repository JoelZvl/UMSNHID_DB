package com.yxhelzvl.umsnh_id.services;

import com.yxhelzvl.umsnh_id.dto.LoginRequest;
import com.yxhelzvl.umsnh_id.dto.LoginResponse;
import com.yxhelzvl.umsnh_id.exception.InvalidCredentialsException;
import com.yxhelzvl.umsnh_id.model.Usuario;
import com.yxhelzvl.umsnh_id.model.enums.ErrorCode;
import com.yxhelzvl.umsnh_id.repositories.UsuarioRepository;
import com.yxhelzvl.umsnh_id.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository,
                       JwtUtil jwtUtil,
                       PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse authenticate(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmailOrMatricula(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException(ErrorCode.USER_NOT_FOUND.getMessage()));

        System.out.println("COntraseña " +usuario.getContrasena());
        if (!passwordEncoder.matches(request.getPassword(), usuario.getContrasena())) {
            throw new InvalidCredentialsException(ErrorCode.INVALID_CREDENTIALS.getMessage());
        }

        // Generar token
        String token = jwtUtil.generateToken(usuario);

        return new LoginResponse(token, usuario);
    }
}