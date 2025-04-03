package com.yxhelzvl.umsnh_id.repositories;


import com.yxhelzvl.umsnh_id.dto.UsuarioDetalleResponse;
import com.yxhelzvl.umsnh_id.exception.InvalidCredentialsException;
import com.yxhelzvl.umsnh_id.model.AdministrativoInfo;
import com.yxhelzvl.umsnh_id.model.EstudianteInfo;
import com.yxhelzvl.umsnh_id.model.Usuario;
import com.yxhelzvl.umsnh_id.model.UsuarioDetallado;
import com.yxhelzvl.umsnh_id.model.enums.ErrorCode;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class UsuarioRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;



    public UsuarioRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UsuarioDetalleResponse findUserById(int idUsuario) {
        String sql = """
            SELECT 
                u.id_usuario, u.nombre, u.apellidoP, u.apellidoM, 
                u.matricula, u.email, u.contraseña, u.id_rol, u.status,
                r.tipo_rol 
            FROM Usuarios u
            INNER JOIN Roles r ON u.id_rol = r.id_rol
            WHERE u.id_usuario = :idUsuario""";

        try {
            UsuarioDetallado usuario = jdbcTemplate.queryForObject(
                    sql,
                    new MapSqlParameterSource("idUsuario", idUsuario),
                    (rs, rowNum) -> new UsuarioDetallado(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("apellidoP"),
                            rs.getString("apellidoM"),
                            rs.getString("matricula"),
                            rs.getString("email"),
                            rs.getString("contraseña"),
                            rs.getInt("id_rol"),
                            rs.getString("status"),
                            rs.getString("tipo_rol")
                    )
            );

            Object rolInfo = obtenerInformacionRol(usuario.getTipoRol(), idUsuario);
            return new UsuarioDetalleResponse(usuario, rolInfo);

        } catch (Exception e) {
            throw e;
        }
    }

    private Object obtenerInformacionRol(String tipoRol, int idUsuario) {
        return switch (tipoRol) {
            case "Estudiante" -> obtenerInfoEstudiante(idUsuario);
            case "Administrativo" -> obtenerInfoAdministrativo(idUsuario);
            //case "Maestro" -> obtenerInfoMaestro(idUsuario);
            default -> null;
        };
    }

    private EstudianteInfo obtenerInfoEstudiante(int idUsuario) {
        String sql = """
            SELECT e.id_carrera, c.carrera, c.facultad 
            FROM Estudiantes e
            JOIN Carreras c ON e.id_carrera = c.id_carrera
            WHERE e.id_usuario = :idUsuario""";

        return jdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource("idUsuario", idUsuario),
                (rs, rowNum) -> new EstudianteInfo(
                        rs.getInt("id_carrera"),
                        rs.getString("carrera"),
                        rs.getString("facultad")
                )
        );
    }

    private AdministrativoInfo obtenerInfoAdministrativo(int idUsuario) {
        String sql = "SELECT departamento FROM Administrativos WHERE id_usuario = :idUsuario";
        return jdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource("idUsuario", idUsuario),
                (rs, rowNum) -> new AdministrativoInfo(rs.getString("departamento"))
        );
    }




    public Integer insertUsuario(Usuario usuario) {
        String sql = "INSERT INTO Usuarios (nombre, apellidoP, apellidoM, matricula, email, contraseña, id_rol, status) " +
                "VALUES (:nombre, :apellidoP, :apellidoM, :matricula, :email, :contrasena, :idRol, :status)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nombre", usuario.getNombre());
        params.addValue("apellidoP", usuario.getApellidoP());
        params.addValue("apellidoM", usuario.getApellidoM());
        params.addValue("matricula", usuario.getMatricula());
        params.addValue("email", usuario.getEmail());
        params.addValue("contrasena", usuario.getContrasena());
        params.addValue("idRol", usuario.getIdRol());
        params.addValue("status", usuario.getStatus());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id_rol"});

        return keyHolder.getKey() != null ? keyHolder.getKey().intValue() : null;
    }

    public Optional<Usuario> findByEmailOrMatricula(String username) {
        String sql = "SELECT * FROM Usuarios WHERE email = :username OR matricula = :username";

        try {
            Usuario usuario = jdbcTemplate.queryForObject(
                    sql,
                    new MapSqlParameterSource("username", username),/*new BeanPropertyRowMapper<>(Usuario.class)*/ // Ahora sí funcionará
                    (rs, rowNum) -> {
                        Usuario u = new Usuario();
                        u.setIdUsuario(rs.getInt("id_usuario"));
                        u.setNombre(rs.getString("nombre"));
                        u.setApellidoP(rs.getString("apellidoP"));
                        u.setApellidoM(rs.getString("apellidoM"));
                        u.setMatricula(rs.getString("matricula"));
                        u.setEmail(rs.getString("email"));
                        u.setContrasena(rs.getString("contraseña"));
                        u.setIdRol(rs.getInt("id_rol"));
                        u.setStatus(rs.getString("status"));

                        return u;
                    }
            );

            return Optional.ofNullable(usuario);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCredentialsException(ErrorCode.USER_NOT_FOUND.getMessage());
        }
    }
}
