package roomescape.time.repository;

import static roomescape.reservation.domain.Reservation.RESERVATION_LENGTH_MINUTES;

import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.time.domain.Time;

@Repository
public class TimeRepository {
    private static final RowMapper<Time> timeRowMapper = (resultSet, rowNum) -> {
        Time time = new Time(
                resultSet.getObject("time", LocalTime.class)
        );
        return time.withId(resultSet.getLong("id"));
    };

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public Time saveTime(Time time) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("time", time.getTime());
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return time.withId(id);
    }

    public List<Time> findAllTimes() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public int deleteTimeById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int countConflictingTimes(LocalTime time) {
        String sql = "SELECT COUNT(*) FROM time WHERE CAST(? AS TIME) BETWEEN DATEADD(MINUTE, -CAST(? AS INT), time) AND DATEADD(MINUTE, CAST(? AS INT), time)";
        return jdbcTemplate.queryForObject(sql, Integer.class, time, RESERVATION_LENGTH_MINUTES,
                RESERVATION_LENGTH_MINUTES);
    }
}
