package com.yxhelzvl.umsnh_id.repositories;

import com.yxhelzvl.umsnh_id.dto.InscripcionInfoDTO;
import com.yxhelzvl.umsnh_id.dto.InscripcionResponse;
import com.yxhelzvl.umsnh_id.dto.UsuarioDetalleResponse;
import com.yxhelzvl.umsnh_id.model.UsuarioDetallado;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InscripcionRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public InscripcionRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertInscripcion(Integer idUsuario, Integer idCiclo){
        String sql = "INSERT INTO Inscripciones (id_usuario, id_ciclo, estado) " +
                "VALUES (:idUsuario, :idCiclo, 'en_proceso')";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("idUsuario", idUsuario)
                .addValue("idCiclo", idCiclo);

        jdbcTemplate.update(sql, params);
    }




    public InscripcionResponse obtenerUltimaInscripcionPorUsuario(int idUsuario) {
        String sql = """
        SELECT 
            i.id_inscripcion,
            CONCAT(u.nombre, ' ', u.apellidoP, ' ', u.apellidoM) AS nombreUsuario,
            u.email,
            c.ciclo AS cicloEscolar,
            i.fecha_inscripcion,
            i.estado,
            c.fecha_inicio AS fechaInicioCiclo,
            c.fecha_final AS fechaFinCiclo
        FROM Inscripciones i
        JOIN Usuarios u ON i.id_usuario = u.id_usuario
        JOIN Ciclos c ON i.id_ciclo = c.id_ciclo
        WHERE i.id_usuario = :idUsuario
        ORDER BY i.fecha_inscripcion DESC
        LIMIT 1""";

        try {
                InscripcionInfoDTO usuario = jdbcTemplate.queryForObject(
                        sql,
                        new MapSqlParameterSource("idUsuario", idUsuario),
                        (rs, rowNum) -> new InscripcionInfoDTO(
                                rs.getInt("id_inscripcion"),
                                rs.getString("nombreUsuario"),
                                rs.getString("email"),
                                rs.getString("cicloEscolar"),
                                rs.getDate("fecha_inscripcion").toLocalDate(),
                                rs.getString("estado"),
                                rs.getDate("fechaInicioCiclo").toLocalDate(),
                                rs.getDate("fechaFinCiclo").toLocalDate()
                        )
                );

                return new InscripcionResponse(usuario);


        } catch (Exception e) {
            throw e;
        }
    }
}
