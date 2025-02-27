package roomescape.repository;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.time.Time;

@Repository
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final RowMapper<Time> timeRowMapper = (rs, rowNum) -> {
        return new Time(rs.getLong("time_id"), rs.getTime("time").toLocalTime());
    };

    public TimeRepository(JdbcTemplate jdbcTemplate, DataSource source) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(source).withTableName("times").usingGeneratedKeyColumns("time_id");
    }

    public Long save(Time time) {
        Map<String, LocalTime> params = new HashMap<>();
        params.put("time", time.getTime());
        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    public Optional<Time> findById(Long id) {
        String sql = "SELECT time_id, time FROM times WHERE time_id = ?";
        return jdbcTemplate.query(sql, timeRowMapper, id).stream().findFirst();
    }

    public List<Time> findAll() {
        List<Time> times = jdbcTemplate.query("select time_id, time from times", timeRowMapper);
        return List.copyOf(times);
    }

    public void delete(Long timeId) {
        String sql = "DELETE FROM times WHERE time_id = ?";
        jdbcTemplate.update(sql, timeId);
    }
}
