package roomescape.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

@Repository
public class TimeDAO {
    private final JdbcTemplate jdbcTemplate;

    public TimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(Time time) {
        jdbcTemplate.update("INSERT INTO time (time) VALUES (?)",
                time.getTime());
    }

    public Optional<Integer> getId(Time time) {
        return jdbcTemplate.query(
                "SELECT id FROM time WHERE time = ? LIMIT 1",
                (rs, rowNum) -> rs.getInt("id"),
                time.getTime()
        ).stream().findFirst();
    }

    public List<Time> findAll() {
        return jdbcTemplate.query("SELECT * FROM time", (rs, rowNum) ->
                new Time(rs.getInt("id"), rs.getString("time"))
        );
    }

    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM time WHERE id = ?", id);
    }

    public Optional<Time> findByTime(String time) {
        return jdbcTemplate.query(
                "SELECT id, time FROM time WHERE time = ? LIMIT 1",
                (rs, rowNum) -> new Time(rs.getInt("id"), rs.getString("time")),
                time
        ).stream().findFirst();
    }
}
