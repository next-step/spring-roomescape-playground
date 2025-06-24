package roomescape.domain;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Time> timeRowMapper = (rs, rowNum) -> Time.of(
            rs.getLong("id"),
            rs.getTime("time").toLocalTime()
    );

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Time> findById(Long id) {
        String sql = "SELECT id, time FROM time WHERE id = ?";
        try {
            Time time = jdbcTemplate.queryForObject(sql, timeRowMapper, id);
            return Optional.of(time);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Time> findAll() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public Time save(Time time) {
        String sql = "INSERT INTO time (time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setTime(1, java.sql.Time.valueOf(time.getTime()));
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        return Time.of(generatedId, time.getTime());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
