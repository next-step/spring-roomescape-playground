package roomescape;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TimeQueryingDAO {
    private JdbcTemplate jdbcTemplate;
    private final RowMapper<Time> actorRowMapper = (resultSet, rowNum) -> {
        Time time = new Time(
                resultSet.getLong("id"),
                resultSet.getString("time")
        );
        return time;
    };
    public TimeQueryingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Time> getTimes() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, actorRowMapper);
    }
    public long createTime(String time) {
        String sql = "INSERT INTO time (time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, time);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    /**
     *
     * @param id
     * @return delete된 id값
     */

    public int deleteTimeById(long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
