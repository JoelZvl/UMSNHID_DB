package com.yxhelzvl.umsnh_id.controllers;

import com.yxhelzvl.umsnh_id.dto.ApiResponse;
import com.yxhelzvl.umsnh_id.dto.EstudianteRequest;
import com.yxhelzvl.umsnh_id.dto.UsuarioDetalleResponse;
import com.yxhelzvl.umsnh_id.model.Usuario;
import com.yxhelzvl.umsnh_id.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.KeyException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;


    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getUserById(@PathVariable int id) {
        try {
            UsuarioDetalleResponse response = usuarioService.obtenerUsuarioDetalle(id);
            return ResponseEntity.ok(ApiResponse.success("Usuario encontrado", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(
                            HttpStatus.NOT_FOUND,
                            "Usuario no encontrado",
                            List.of("El ID proporcionado no existe")
                    ));
        }
    }



    @PostMapping("/estudiante")
    public ResponseEntity<ApiResponse<?>> crearEstudiante(
            @RequestBody @Valid EstudianteRequest request,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(
                    ApiResponse.error(
                            HttpStatus.BAD_REQUEST,
                            "Datos inválidos",
                            errors
                    )
            );
        }

        try {
            Usuario usuario = new Usuario();
            // Mapeo corregido (asegurar matching de nombres)
            usuario.setNombre(request.getNombre());
            usuario.setApellidoP(request.getApellidoP());
            usuario.setApellidoM(request.getApellidoM());
            usuario.setMatricula(request.getMatricula());
            usuario.setEmail(request.getEmail());
            usuario.setContrasena(request.getContraseña()); // Asegurar matching de nombre de campo
            usuario.setIdRol(request.getIdRol());
            usuario.setStatus("activo");


            usuarioService.crearUsuarioCompleto(usuario, request.getIdCarrera());

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponse.created( // Especifica el tipo genérico como Void
                            "Estudiante creado exitosamente",
                            null // Cuando no hay data, usa Void como tipo
                    )
            );

        } catch (Exception e) {
            // Dejar que el GlobalExceptionHandler maneje esta excepción
            throw e;
        }
    }
}
