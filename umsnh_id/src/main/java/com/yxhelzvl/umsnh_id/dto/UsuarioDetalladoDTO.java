package com.yxhelzvl.umsnh_id.dto;

public class UsuarioDetalladoDTO {
    private Integer idUsuario;
    private String nombre;
    private String tipoRol;

    // Campos específicos por rol
    private String carrera;          // Solo para Estudiantes
    private String departamento;     // Solo para Administrativos

    // Getters y Setters
}
