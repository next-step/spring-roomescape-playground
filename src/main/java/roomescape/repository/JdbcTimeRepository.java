package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Time;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class JdbcTimeRepository implements TimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Time save(Time time) {
        String sql = "INSERT INTO time (time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, time.time());
            return ps;
        }, keyHolder);

        int id = keyHolder.getKey().intValue();
        return new Time(id, time.time());
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM time WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<Time> findAll() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new Time(rs.getInt("id"), rs.getString("time")));
    }

    @Override
    public Time findById(int id) {
        String sql = "SELECT id, time FROM time WHERE id = ?";
        List<Time> result = jdbcTemplate.query(sql, (rs, rowNum) -> new Time(rs.getInt("id"), rs.getString("time")), id
        );
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public Time findByTime(String timeValue) {
        String sql = "SELECT id, time FROM time WHERE time = ?";
        List<Time> result = jdbcTemplate.query(sql, (rs, rowNum) -> new Time(rs.getInt("id"), rs.getString("time")), timeValue);
        return result.isEmpty() ? null : result.get(0);
    }
}
