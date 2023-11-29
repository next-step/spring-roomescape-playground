package roomescape.repository;

import static roomescape.query.ReservationQuery.*;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import roomescape.domain.Reservation;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> getReservations() {
        return jdbcTemplate.query(SELECT_RESERVATIONS.getQuery(),
            (rs, rowNum) -> new Reservation(rs.getLong("id"),
                rs.getString("name"), rs.getString("date"), rs.getString("time")));
    }
}
