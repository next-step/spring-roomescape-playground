package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
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

    public List<Time> findAll() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Time(
                rs.getLong("id"),
                rs.getObject("time", LocalTime.class)
        ));
    }

    public Time save(Time time) {
        String sql = "INSERT INTO time (time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setObject(1, time.getTime());
            return ps;
        }, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return new Time(id, time.getTime());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public Optional<Time> findById(Long id) {
        String sql = "SELECT id, time FROM time WHERE id = ?";
        try {
            Time time = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Time(
                    rs.getLong("id"),
                    rs.getObject("time", LocalTime.class)
            ), id);
            return Optional.ofNullable(time);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}