package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Time;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TimeDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Time> timeRowMapper = (rs, rowNum) -> new Time(
            rs.getLong("id"),
            rs.getString("time")
    );

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Time> findAll() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public Time save(Time time) {
        String sql = "INSERT INTO time (time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, time.getTime());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new Time(id, time.getTime());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
