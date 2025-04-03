package com.yxhelzvl.umsnh_id.services;

import com.yxhelzvl.umsnh_id.dto.InscripcionInfoDTO;
import com.yxhelzvl.umsnh_id.dto.InscripcionResponse;
import com.yxhelzvl.umsnh_id.repositories.InscripcionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscripcionService {
    private final InscripcionRepository inscripcionRepository;

    public InscripcionService(InscripcionRepository inscripcionRepository) {
        this.inscripcionRepository = inscripcionRepository;
    }

    public InscripcionResponse obtenerInscripcionPorUsuario(int idUsuario) {
        return inscripcionRepository.obtenerUltimaInscripcionPorUsuario(idUsuario);
    }
}
