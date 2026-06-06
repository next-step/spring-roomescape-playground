package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

@Repository
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Time> findAll() {
        String sql = "SELECT id, time FROM time";

        return jdbcTemplate.query(sql, timeRowMapper());
    }

    public Optional<Time> findById(Long id) {
        String sql = "SELECT id, time FROM time WHERE id = ?";

        List<Time> times = jdbcTemplate.query(sql, timeRowMapper(), id);

        return times.stream().findAny();
    }

    public Time save(LocalTime time) {
        String sql = "INSERT INTO time (time) VALUES (?)";

        Long id = updateAndGetId(sql, ps -> {
            ps.setTime(1, java.sql.Time.valueOf(time));
        });

        return new Time(id, time);
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";

        return jdbcTemplate.update(sql, id) > 0;
    }

    private Long updateAndGetId(String sql, PreparedStatementSetter setter) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setter.setValues(ps);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    private RowMapper<Time> timeRowMapper() {
        return (rs, rowNum) -> new Time(
                rs.getLong("id"),
                rs.getTime("time").toLocalTime()
        );
    }
}
