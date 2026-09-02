package roomescape.repository;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.exception.DuplicateTimeException;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Time> findAll() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public Time save(Time time) {
        String sql = "INSERT INTO time (time) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});

                ps.setObject(1, time.getTime());
                return ps;
            }, keyHolder);
        } catch (DuplicateKeyException e) {
            throw new DuplicateTimeException("이미 등록된 시간입니다. time=" + time.getTime());
        }

        Long id = keyHolder.getKey().longValue();

        return time.withId(id);
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    private final RowMapper<Time> timeRowMapper = (resultSet, rowNum) -> {
        Time time = new Time(
                resultSet.getObject("time", LocalTime.class)
        );
        return time.withId(resultSet.getLong("id"));
    };
}
