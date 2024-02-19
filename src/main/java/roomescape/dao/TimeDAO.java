package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TimeDAO {
    private final JdbcTemplate jdbcTemplate;

    public TimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(Time time) {
        String sql = "insert into time (time) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql, new String[]{"id"});
            ps.setObject(1, time.getTime());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Time> findAllTimes() {
        String sql = "select id, time from time";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    Time time = new Time(
                            resultSet.getLong("id"),
                            resultSet.getString("time")
                    );
                    return time;
                });
    }

    public int delete(Long id) {
        String sql = "delete from time where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
