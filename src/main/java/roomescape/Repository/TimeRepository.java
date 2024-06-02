package roomescape.Repository;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.Exception.NotFoundReservationException;
import roomescape.Entity.ReservationTime;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TimeRepository {

    private JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    private final RowMapper<ReservationTime> rowMapper = (rs, rowNum) ->new ReservationTime(
            rs.getLong("id"),
            rs.getString("time")
    );

    public List<ReservationTime> getAllTimes() {
        String sql = "SELECT * FROM time";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Long addTime(ReservationTime time) {
        String sql = "INSERT INTO time(time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, time.getTime());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public ReservationTime getTimeById(Long id) {
        String sql = "SELECT id, time FROM time WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundReservationException("시간을 찾을 수 없습니다.");
        }
    }

    public void deleteTimeById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        if (rowsAffected == 0) {
            throw new NotFoundReservationException("시간을 찾을 수 없습니다.");
        }
    }
}
