package com.yxhelzvl.umsnh_id.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Repository
public class RolRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RolRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getTipoRolById(Integer idRol) {
        String sql = "SELECT tipo_rol FROM Roles WHERE id_rol = :idRol";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("idRol", idRol);

        try {
            return jdbcTemplate.queryForObject(sql, params, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer findIdByTipoRol(String tipoRol) {
        String sql = "SELECT id_rol FROM Roles WHERE tipo_rol = :tipoRol";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("tipoRol", tipoRol);

        try {
            return jdbcTemplate.queryForObject(sql, params, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
