package roomescape.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TimeDao {
    private JdbcTemplate jdbcTemplate;

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Time> timeRowMapper = (ResultSet, rownumber)
            -> new Time(
                ResultSet.getInt("id"),
                ResultSet.getString("time")
        );
    public List<Time> getAllTime() {
        String sql = "SELECT id,time FROM time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }
    public int save(Time time) {
        String sql = "INSERT INTO time (time) VALUES (?)";
        return jdbcTemplate.update(sql, time.toString());
    }
    public Time getTimeById(int id) {
        String sql = "SELECT id,time FROM time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, timeRowMapper);
    }
    public void deleteTimeById(int id) {
        String sql = "DELETE FROM time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}