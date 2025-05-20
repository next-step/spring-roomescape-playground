package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

@Repository
public class TimeDAO {

    private final JdbcTemplate jdbcTemplate;

    public TimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Time save(Time time) {
        final var query = "INSERT INTO time (time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setString(1, time.getTime());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new Time(id, time.getTime());
    }

    public List<Time> findAll() {
        return jdbcTemplate.query("SELECT * FROM time", timeRowMapper);
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM time WHERE id = ?", id);
    }

    private final RowMapper<Time> timeRowMapper = (rs, rowNum) ->
            new Time(rs.getLong("id"), rs.getString("time"));
}
