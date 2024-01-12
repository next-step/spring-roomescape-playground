package roomescape.time.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.time.domain.Time;

import java.util.List;

@Repository
public class TimeQueryRepository {
    private JdbcTemplate jdbcTemplate;

    public TimeQueryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Time> timeRowMapper  = (resultSet, rowNum) -> {
        Time time = new Time(
                resultSet.getLong("id"),
                resultSet.getTime("time").toLocalTime()
        );
        return time;
    };

    public List<Time> getAllTimes() {
        String sql = "select id, time FROM time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public Time findById(Long time_id) {
        String sql = "SELECT * FROM time WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{time_id}, timeRowMapper);
    }
}
