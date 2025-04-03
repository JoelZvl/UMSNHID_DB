package com.yxhelzvl.umsnh_id.model;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioDetallado {
    private int idUsuario;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String matricula;
    private String email;
    private String password;
    private int idRol;
    private String status;
    private String tipoRol;
}