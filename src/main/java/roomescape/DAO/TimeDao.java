package roomescape.DAO;

import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TimeDao {
    private final JdbcTemplate jdbcTemplate;

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<LocalTime> findAllValidTimes() {
        String sqlQuery = "SELECT * FROM ValidTimes";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) ->
                rs.getTime("time").toLocalTime()
        );
    }

    public void saveValidTime(LocalTime time) {
        String sqlQuery = "INSERT INTO ValidTimes(time) VALUES (?)";
        jdbcTemplate.update(sqlQuery, time);
    }

    public void deleteValidTime(LocalTime requestTime) {
        String deleteQuery = "DELETE FROM ValidTimes WHERE time = ?";
        jdbcTemplate.update(deleteQuery, requestTime);
    }
}
