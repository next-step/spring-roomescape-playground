package roomescape.dao;

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

    public void refreshValidTimesScheduler() {
        String deleteSql = "DELETE FROM ValidTimes WHERE date < CURRENT_DATE";
        jdbcTemplate.update(deleteSql);

        String insertFutureSql =
                "INSERT INTO ValidTimes(date, time) " +
                        "SELECT " +
                        "  DATEADD('DAY', 7, CURRENT_DATE), " +
                        "  PARSEDATETIME(t.X || ':00:00', 'H:mm:ss') " +
                        "FROM SYSTEM_RANGE(8, 21) AS t " +
                        "WHERE NOT EXISTS (" +
                        "  SELECT 1 FROM ValidTimes v " +
                        "  WHERE v.date = DATEADD('DAY', 7, CURRENT_DATE) " +
                        "  AND v.time = PARSEDATETIME(t.X || ':00:00', 'H:mm:ss')" +
                        ")";

        jdbcTemplate.update(insertFutureSql);
    }
}
