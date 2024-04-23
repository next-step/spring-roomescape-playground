package roomescape.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.Domain.Time;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TimeRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Time> getAllTime() {
        List<Time> timeList = jdbcTemplate.query(
                "select id, time from time",
                (resultSet, rowNum) -> {
                    Time time = new Time(
                            resultSet.getLong("id"),
                            resultSet.getString("time")
                    );
                    return time;
                });

        return timeList;
    }

    public Time createTime(Time time) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into time (time) values (?)",
                    new String[]{"id"});
            ps.setString(1, time.getTime());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        Time newTime = new Time(id, time.getTime());

        return newTime;
    }

    public void deleteTimeById(Long id) {
        try {
            String sql = "DELETE FROM time WHERE id = ?";

            Integer count = jdbcTemplate.queryForObject("SELECT count(*) from time where id = ?", Integer.class, id);

            jdbcTemplate.update(sql, id);
        } catch (IncorrectResultSizeDataAccessException error) {
            throw error;
        }
    }
}
