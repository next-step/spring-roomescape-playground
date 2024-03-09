package roomescape.web.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.web.dao.rowmapper.TimeRowMapper;

import java.util.List;
import java.util.Optional;


@Repository
public class JdbcTimeDao implements TimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final TimeRowMapper timeRowMapper;

    public JdbcTimeDao(JdbcTemplate jdbcTemplate, TimeRowMapper timeRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.timeRowMapper = timeRowMapper;
    }

    @Override
    public List<Time> getAllTimes() {
        String sql = "SELECT * FROM time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    @Override
    public Time createTime(Long id, String time) {
        String sql = "INSERT INTO time (id, time) VALUES (?, ?)";
        jdbcTemplate.update(sql, id, time);

        String selectSql = "SELECT * FROM time WHERE time = ?";
        return jdbcTemplate.queryForObject(selectSql, new Object[]{time}, timeRowMapper);
    }

    @Override
    public void deleteTimeById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Time> getTimeById(Long id) {
        String selectSql = "SELECT id, time FROM time WHERE id = ? ";
        return Optional.ofNullable(jdbcTemplate.queryForObject(selectSql, new Object[]{id}, timeRowMapper));
    }

}
