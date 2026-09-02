package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.time.TimeRequest;

import java.sql.PreparedStatement;
import roomescape.domain.time.Time;
import java.time.LocalTime;
import java.util.List;

@Repository
public class TimeDAO {
    private final JdbcTemplate jdbcTemplate;

    public TimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Time> findAllTimes() {
        String sql = "select id, time from time";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    Time time = new Time(
                            resultSet.getLong("id"),
                            resultSet.getObject("time", LocalTime.class)
                    );
                    return time;
                });
    }

    public int delete(Long id) {
        int delete = jdbcTemplate.update("delete from time where id = ?", id);
        return delete;
    }

    public Long insertWithKeyHolder(TimeRequest request) {
        String sql = "insert into time (time) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setObject(1, request.getTime());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Time findById(Long id) {
        String sql = "select id, time from time where id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> new Time(
                        resultSet.getLong("id"),
                        resultSet.getObject("time", LocalTime.class)
                ),
                id);
    }
}
