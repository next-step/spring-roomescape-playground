package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.entity.Time;

import java.util.List;

@Repository
public class TimeDao {
    public final JdbcTemplate jdbcTemplate;

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Time> findAll() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Time(
                rs.getLong("id"),
                rs.getString("time")
        ));
    }

    public Time insert(Time time) {
        String sql = "INSERT INTO time(time) VALUES (?)";
        jdbcTemplate.update(sql, time.getTime());

        String query = "SELECT id FROM time ORDER BY id DESC LIMIT 1";
        Long id = jdbcTemplate.queryForObject(query, Long.class);

        return new Time(id, time.getTime());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
