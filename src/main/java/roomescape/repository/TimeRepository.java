package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Time;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public TimeRepository(
            JdbcTemplate jdbcTemplate
    ) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Time> timeRowMapper = (rs, rowNum) -> {
        return Time.of(
                rs.getLong("id"),
                rs.getObject("time", LocalTime.class)
        );
    };

    public List<Time> findAll() {
        String sql = "SELECT id,time FROM time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public Time save(Time time) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("time", time.getTime());

        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);

        return Time.of(
                key.longValue(),
                time.getTime()
        );
    }

    public Time findById(Long id) {
        String sql = "SELECT id,time FROM time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, timeRowMapper, id);
    }

    public void deleteById(Long id) {
        String deleteSql = "DELETE FROM time WHERE id = ? ";
        jdbcTemplate.update(deleteSql, id);
    }
}
