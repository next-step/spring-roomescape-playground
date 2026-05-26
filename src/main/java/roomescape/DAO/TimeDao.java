package roomescape.DAO;

import java.time.LocalDate;
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

    public List<LocalTime> findAllValidTimes(LocalDate date) {
        String sqlQuery = "SELECT time FROM ValidTimes WHERE date = ?";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> rs.getTime("time").toLocalTime(), date);
    }

    public List<LocalTime> findValidTimesByDate(LocalDate date) {
        String sql = "SELECT time FROM ValidTimes WHERE date = ? ORDER BY time ASC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getTime("time").toLocalTime(), date);
    }

    public void saveValidTime(LocalDate date, LocalTime time) {
        String sql = "INSERT INTO ValidTimes(date, time) VALUES (?, ?)";
        jdbcTemplate.update(sql, date, time);
    }

    public void deleteValidTime(LocalDate date, LocalTime time) {
        String sqlQuery = "DELETE FROM ValidTimes WHERE date = ? AND time = ?";
        jdbcTemplate.update(sqlQuery, date, time);
    }
}
