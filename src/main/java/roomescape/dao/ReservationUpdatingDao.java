package roomescape.dao;

import java.sql.Date;
import java.sql.PreparedStatement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import roomescape.domain.dto.ReservationAddRequest;

@Repository
public class ReservationUpdatingDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationUpdatingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long createReservation(ReservationAddRequest reservationAddRequest) {
        String sql = "INSERT INTO reservation (name, date, time_id) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                sql,
                new String[] {"id"});
            ps.setString(1, reservationAddRequest.getName());
            ps.setDate(2, Date.valueOf(reservationAddRequest.getDate()));
            ps.setLong(3, reservationAddRequest.getTime());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public int deleteReservation(Long id) {
        String sql = "DELETE FROM reservation where id = ?";

        return jdbcTemplate.update(sql, id);
    }
}
