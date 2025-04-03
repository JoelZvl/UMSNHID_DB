package com.yxhelzvl.umsnh_id.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CicloRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CicloRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer getUltimoCicloId() {
        String sql = "SELECT id_ciclo FROM Ciclos ORDER BY fecha_inicio DESC LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
