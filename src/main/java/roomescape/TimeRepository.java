package roomescape;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.exception.NotFoundException;


@Repository
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Time findTimeById(long id) {
        String sql = "SELECT id, time FROM time WHERE id = ?";

        Time time = jdbcTemplate.queryForObject(
            sql,
            (rs, rowNum) -> Time.create(
                rs.getLong("id"),
                rs.getObject("time", LocalTime.class)
            ),
            id
        );
        if (time == null) {
            throw new NotFoundException(id + "번의 time이 존재하지 않습니다.");
        }
        return time;
    }

    public List<Time> readTimes() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> Time.create(
                rs.getLong("id"),
                rs.getObject("time", LocalTime.class)
            )
        );
    }

    public void deleteTime(long id) {
        int deletedCount = jdbcTemplate.update(
            "DELETE FROM time WHERE id = ?",
            id
        );
        if (deletedCount == 0) {
            throw new NotFoundException("Time not found: id=" + id);
        }
    }

    public Time createTime(TimeRequest timeRequest) {

        Time newTime = Time.create(timeRequest.getTime());

        String sql = "INSERT INTO time(time) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                sql,
                new String[]{"id"}
            );

            ps.setObject(1, newTime.getTime());

            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();

        Time time = newTime.withId(id);

        return time;
    }
}
