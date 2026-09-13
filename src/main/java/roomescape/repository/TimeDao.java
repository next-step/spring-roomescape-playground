package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TimeDao {
    private final JdbcTemplate jdbcTemplate;

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Time> timeRowMapper = (resultSet, rowNum) -> {
        Time time = new Time(
                resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("time"))
        );
        return time;
    };

    public List<Time> findAllTimes() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public int deleteTime(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public Long insert(Time time) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("time", time.getTime());

        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    public Time findById(Long id) {
        String sql = "SELECT id, time FROM time WHERE id = ?";

        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new Time(rs.getLong("id"), LocalTime.parse(rs.getString("time"))), id);
    }
}
