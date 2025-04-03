package com.yxhelzvl.umsnh_id.controllers;

import com.yxhelzvl.umsnh_id.dto.ApiResponse;
import com.yxhelzvl.umsnh_id.dto.InscripcionInfoDTO;
import com.yxhelzvl.umsnh_id.dto.InscripcionResponse;
import com.yxhelzvl.umsnh_id.services.InscripcionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }



    @GetMapping(value = "/usuario/{id}")
    public ResponseEntity<ApiResponse<InscripcionResponse>>obtenerInscripcionesPorUsuario(@PathVariable int id) throws Exception {
        return ResponseEntity.ok(
                ApiResponse.success("Inscripciones del usuario obtenidas", inscripcionService.obtenerInscripcionPorUsuario(id))
        );
    }
}
