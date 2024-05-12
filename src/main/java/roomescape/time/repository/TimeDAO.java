package roomescape.time.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.time.Time;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TimeDAO {

    private JdbcTemplate jdbcTemplate;

    public TimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Time save(Time time) {
        String sql = "insert into time (time) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, time.getTime());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new Time(id, time.getTime());
    }

    public List<Time> findAll() {
        String sql = "select id, time from time";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    Time time = new Time(
                            rs.getLong("id"),
                            rs.getString("time")
                    );
                    return time;
                });
    }

    public void deleteTime(Long id) {
        String sql = "delete from time where id = ?";
        jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
