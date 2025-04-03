package com.yxhelzvl.umsnh_id.dto;

import com.yxhelzvl.umsnh_id.model.UsuarioDetallado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDetalleResponse {
    private UsuarioDetallado usuario;
    private Object Info;
}