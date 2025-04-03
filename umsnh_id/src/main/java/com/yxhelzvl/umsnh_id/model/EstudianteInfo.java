package com.yxhelzvl.umsnh_id.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteInfo {
    private int idCarrera;
    private String carrera;
    private String facultad;
}