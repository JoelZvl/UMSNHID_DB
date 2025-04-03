package com.yxhelzvl.umsnh_id.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstudianteRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido paterno es obligatorio")
    private String apellidoP;

    @NotBlank(message = "El apellido materno es obligatorio")
    private String apellidoM;

    @NotBlank(message = "La matrícula es obligatoria")
    @Size(min = 8, max = 15, message = "La matrícula debe tener entre 8 y 15 caracteres")
    private String matricula;

    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contraseña;

    @NotNull(message = "El rol es requerido")
    private Integer idRol;

    @NotNull(message = "La carrera es requerida")
    private Integer idCarrera;

    // Getters y Setters
}
