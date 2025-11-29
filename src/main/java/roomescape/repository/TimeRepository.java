package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Time;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("time")
            .usingGeneratedKeyColumns("id");
    }

    public List<Time> findAll() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Time(
            rs.getLong("id"),
            rs.getString("time")
        ));
    }

    public Time save(Time time) {
        Map<String, Object> params = new HashMap<>();
        params.put("time", time.getTime());

        Number key = jdbcInsert.executeAndReturnKey(params);
        time.setId(key.longValue());
        return time;
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        int deletedRows = jdbcTemplate.update(sql,id);
        return deletedRows > 0;
    }
}
