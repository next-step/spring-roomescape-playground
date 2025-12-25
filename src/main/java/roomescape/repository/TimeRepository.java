package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Time> timeRowMapper = (rs, rowNum) -> new Time(
            rs.getLong("id"),
            rs.getObject("time", LocalTime.class)
    );

    public List<Time> findAll() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public Optional<Time> findById(Long id) {
        String sql = "SELECT id, time FROM time WHERE id = ?";
        List<Time> results = jdbcTemplate.query(sql, timeRowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public Time save(Time time) {
        String sql = "INSERT INTO time(time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                    ps.setObject(1, time.getTime());
                    return ps;
                }, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return new Time(id, time.getTime());
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        int count = jdbcTemplate.update(sql, id);
        return count > 0;
    }
}
