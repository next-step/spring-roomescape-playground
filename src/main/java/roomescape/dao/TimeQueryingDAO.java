package roomescape.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
@Repository
public class TimeQueryingDAO {
    private static TimeQueryingDAO instance = null;
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Time> timeRowMapper = (resultSet, rowNum) -> {
        Time time = Time.toEntity(resultSet.getLong("id"), resultSet.getString("time"));
        return time;
    };

    protected TimeQueryingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Time> findAllTimes() {
        String sql = "select * from time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public Time findTimeById(String id) {
        String sql = "select * from time where id = ?";
        return jdbcTemplate.queryForObject(sql, timeRowMapper, id);
    }
}
