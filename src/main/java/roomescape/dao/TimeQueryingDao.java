package roomescape.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import roomescape.domain.Time;

@Repository
public class TimeQueryingDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Time> timeRawMapper = (resultSet, rowNum) ->
        new Time(resultSet.getLong("id"),
            resultSet.getTime("time").toLocalTime());

    public TimeQueryingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Time> listAllTimes() {
        String sql = "SELECT id, time FROM time";

        return jdbcTemplate.query(sql, timeRawMapper);
    }

    public List<Time> getTime(Long id) {
        String sql = "SELECT id, time FROM time WHERE id = ?";

        return jdbcTemplate.query(sql, timeRawMapper, id);
    }
}
