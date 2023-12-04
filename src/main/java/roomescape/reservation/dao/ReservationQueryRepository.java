package roomescape.reservation.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.Time;

import java.util.List;

@Repository
public class ReservationQueryRepository {
    private JdbcTemplate jdbcTemplate;

    public ReservationQueryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper  = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                new Time(resultSet.getLong("time_id"), resultSet.getTime("time_value").toLocalTime())
        );
        return reservation;
    };


    public List<Reservation> getAllReservations() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value FROM reservation as r INNER JOIN time as t ON r.time_id = t.id";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }
    
}
