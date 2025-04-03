package com.yxhelzvl.umsnh_id.config;

import com.yxhelzvl.umsnh_id.repositories.RolRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final RolRepository rolRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DataInitializer(RolRepository rolRepository,
                           NamedParameterJdbcTemplate jdbcTemplate) {
        this.rolRepository = rolRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        insertRoleIfNotExists("Estudiante");
        insertRoleIfNotExists("Maestro");
        insertRoleIfNotExists("Administrativo");
        insertRoleIfNotExists("Bibliotecario");
    }

    private void insertRoleIfNotExists(String tipoRol) {
        if (rolRepository.findIdByTipoRol(tipoRol) == null) {
            String sql = "INSERT INTO Roles (tipo_rol) VALUES (:tipoRol)";
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("tipoRol", tipoRol);

            try {
                jdbcTemplate.update(sql, params);
                System.out.println("Rol insertado: " + tipoRol);
            } catch (DataAccessException e) {
                System.err.println("Error insertando rol: " + e.getMessage());
            }
        }
    }
}
