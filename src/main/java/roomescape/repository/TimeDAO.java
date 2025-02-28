package roomescape.repository;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.error.ErrorMessage;
import roomescape.error.exception.InvalidValueException;
import roomescape.mapper.TimeRowMapper;

@Repository
public class TimeDAO {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final TimeRowMapper timeRowMapper;

    public TimeDAO(JdbcTemplate jdbcTemplate, TimeRowMapper timeRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("time")
            .usingGeneratedKeyColumns("id");
        this.timeRowMapper = timeRowMapper;
    }

    public Time createTime(Time time) {
        Map<String, Object> params = new HashMap<>();
        params.put("time", time.getTime());
        long key = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return new Time(key, time.getTime());
    }

    public List<Time> findTimes() {
        String sql = "select id, time from time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public void deleteTime(Long timeId) {
        String sql = "delete from time where id = ?";
        jdbcTemplate.update(sql, timeId);
    }

    public Time findTime(Long timeId) {
        String sql = "select * from time where id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, timeRowMapper, timeId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidValueException(ErrorMessage.NO_TIME.getMessage());
        }
    }

    public boolean existsTime(LocalTime time) {
        String sql = "select exists(select 1 from time where time = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, time);
    }
}
