package roomescape.domain.time.dao;

import static java.util.Objects.requireNonNull;
import static roomescape.domain.reservation.exception.ReservationException.ErrorCode.NOT_FOUND;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.domain.reservation.exception.ReservationException;
import roomescape.domain.time.entity.Time;

public class JdbcTimeRepository implements TimeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Time save(Time time) {
        String sql = "INSERT INTO time (time) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, String.valueOf(time.getTime()));
            return ps;
        }, keyHolder);

        return Time.builder()
                .id(requireNonNull(keyHolder.getKey()).longValue())
                .time(time.getTime())
                .build();
    }

    @Override
    public Time findById(long timeId) {
        String sql = "SELECT * FROM time WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{timeId}, new TimeMapper());
        } catch (EmptyResultDataAccessException ex) {
            throw new ReservationException(NOT_FOUND);
        }
    }

    @Override
    public Time findByTime(LocalTime time) {
        String sql = "SELECT * FROM time WHERE time = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{time}, new TimeMapper());
        } catch (EmptyResultDataAccessException ex) {
            throw new ReservationException(NOT_FOUND);
        }
    }

    @Override
    public void delete(Time time) {
        Time findTime = findById(time.getId());
        String sql = "DELETE FROM time WHERE id = ?";
        jdbcTemplate.update(sql, findTime.getId());
    }

    @Override
    public void deleteById(long timeId) {
        Time time = findById(timeId);
        delete(time);
    }

    @Override
    public List<Time> findAll() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, new TimeMapper());
    }

    private static class TimeMapper implements RowMapper<Time> {
        @Override
        public Time mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Time.builder()
                    .id(rs.getLong("id"))
                    .time(LocalTime.parse(rs.getString("time")))
                    .build();
        }
    }
}
