package com.yxhelzvl.umsnh_id.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscripcionInfoDTO {
    private int idInscripcion;
    private String nombreUsuario;
    private String email;
    private String cicloEscolar;
    private LocalDate fechaInscripcion;
    private String estado;
    private LocalDate fechaInicioCiclo;
    private LocalDate fechaFinCiclo;
}