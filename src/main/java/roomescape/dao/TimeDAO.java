package roomescape.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TimeDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Time> rowMapper = (resultSet, rowNum) -> {
        Time time = new Time(
                resultSet.getLong("id"),
                resultSet.getString("time"));
        return time;
    };

    public List<Time> findAllTime() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Time findByTimeValue(String timeValue) {
        String sql = "SELECT id, time FROM time where time = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, timeValue);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Time insert(Time time) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO time(time) VALUES (?)", new String[]{"id"});
            ps.setString(1, time.getTime());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new Time(id, time.getTime());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
