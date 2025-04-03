package com.yxhelzvl.umsnh_id.services;

import com.yxhelzvl.umsnh_id.dto.UsuarioDetalleResponse;
import com.yxhelzvl.umsnh_id.model.Usuario;
import com.yxhelzvl.umsnh_id.repositories.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepo;
    private final EstudianteRepository estudianteRepo;
    private final InscripcionRepository inscripcionRepo;
    private final CicloRepository cicloRepo;
    private final RolRepository rolRepository; // Necesario para validar el rol

    public UsuarioService(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepo, EstudianteRepository estudianteRepo,
                          InscripcionRepository inscripcionRepo, CicloRepository cicloRepo,
                          RolRepository rolRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepo = usuarioRepo;
        this.estudianteRepo = estudianteRepo;
        this.inscripcionRepo = inscripcionRepo;
        this.cicloRepo = cicloRepo;
        this.rolRepository = rolRepository;
    }



    public UsuarioDetalleResponse obtenerUsuarioDetalle(int idUsuario) {
        return usuarioRepo.findUserById(idUsuario);
    }



    @Transactional
    public void crearUsuarioCompleto(Usuario usuario, Integer idCarrera) {
        // 1. Validar rol estudiante
        String tipoRol = rolRepository.getTipoRolById(usuario.getIdRol());
        if (!"Estudiante".equalsIgnoreCase(tipoRol)) {
            throw new IllegalArgumentException("El rol no corresponde a un estudiante");
        }
        // Encriptar contraseña antes de guardar
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        // 2. Insertar usuario
        Integer idUsuario = usuarioRepo.insertUsuario(usuario);
        if (idUsuario == null) {
            throw new RuntimeException("Error al crear el usuario");
        }

        // 3. Insertar estudiante
        estudianteRepo.insertEstudiante(idUsuario, idCarrera);

        // 4. Obtener último ciclo
        Integer idCiclo = cicloRepo.getUltimoCicloId();
        if (idCiclo == null) {
            throw new IllegalStateException("No hay ciclos registrados");
        }

        // 5. Crear inscripción
        inscripcionRepo.insertInscripcion(idUsuario, idCiclo);
    }



}
