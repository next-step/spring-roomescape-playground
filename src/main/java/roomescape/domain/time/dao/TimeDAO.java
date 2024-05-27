package roomescape.domain.time.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.domain.time.Time;
import roomescape.domain.time.dto.TimeDTO;

@Component
public class TimeDAO {
    private final JdbcTemplate jdbcTemplate;

    public TimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<TimeDTO> reservationDtoRowMapper = (resultSet, rowNum) -> {
        return new TimeDTO(
                resultSet.getLong("id"),
                resultSet.getString("time")
        );
    };

    public List<TimeDTO> findAll() {
        String sql = "select id, time from time";
        return jdbcTemplate.query(sql, reservationDtoRowMapper);
    }

    public Time insert(Time time) {
        String sql = "insert into time (time) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, time.getTime().toString());
            return ps;
        }, keyHolder);

        time.setId(keyHolder.getKey().longValue());
        return time;
    }

    public void deleteById(long id) {
        String sql = "delete from time where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
