package roomescape.repository;

import java.util.List;
import roomescape.domain.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.mapper.ReservationRowMapper;

@Repository
public class ReservationDAO {
    private final JdbcTemplate jdbcTemplate;
    private final ReservationRowMapper reservationRowMapper;

    public ReservationDAO(JdbcTemplate jdbcTemplate, ReservationRowMapper reservationRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationRowMapper = reservationRowMapper;
    }

    public List<Reservation> findReservations() {
        String sql = "select id, name, reservation_date, reservation_time from reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }
}