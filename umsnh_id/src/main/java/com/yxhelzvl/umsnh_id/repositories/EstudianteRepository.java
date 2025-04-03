package com.yxhelzvl.umsnh_id.repositories;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EstudianteRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public EstudianteRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertEstudiante(Integer idUsuario, Integer idCarrera) {
        String sql = "INSERT INTO Estudiantes (id_usuario, id_carrera) VALUES (:idUsuario, :idCarrera)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("idUsuario", idUsuario)
                .addValue("idCarrera", idCarrera);

        jdbcTemplate.update(sql, params);
    }
}
