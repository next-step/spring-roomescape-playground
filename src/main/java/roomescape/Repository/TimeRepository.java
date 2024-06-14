package roomescape.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
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

    public Time save(Time time) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO time(time) VALUES(?)", new String[]{"id"});
            ps.setString(1, time.getTime());
            return ps;
        }, keyHolder);

        time.setId(keyHolder.getKey().longValue());
        return time;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM time WHERE id = ?", id);
    }

    public List<Time> findAll() {
        return jdbcTemplate.query("SELECT * FROM time",
                (rs, rowNum) -> new Time(rs.getLong("id"), rs.getString("time")));
    }

    // findById 추가
    public Optional<Time> findById(Long id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM time WHERE id = ?",
                    (rs, rowNum) -> new Time(rs.getLong("id"), rs.getString("time")), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

}

