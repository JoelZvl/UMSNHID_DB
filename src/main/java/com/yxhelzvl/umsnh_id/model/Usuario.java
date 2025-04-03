package com.yxhelzvl.umsnh_id.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usuario {

    private Integer idUsuario;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String matricula;
    private String email;
    @JsonIgnore
    private String contrasena;
    private Integer idRol;
    private String status;

}
