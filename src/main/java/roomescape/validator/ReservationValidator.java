package roomescape.validator;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import roomescape.domain.Time;
import roomescape.domain.Reservation;
import java.util.List;
import roomescape.domain.Reservations;

@Component
public class ReservationValidator {

    private final JdbcTemplate jdbcTemplate;

    public ReservationValidator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void validateDuplicate(Reservation target) {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                "FROM reservation as r " +
                "INNER JOIN time as t ON r.time_id = t.id " +
                "WHERE r.date = ?";

        List<Reservation> reservationList = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    Time time = new Time(
                            rs.getLong("time_id"),
                            rs.getTime("time_value").toLocalTime()
                    );
                    return new Reservation(
                            rs.getLong("reservation_id"),
                            rs.getString("name"),
                            rs.getDate("date").toLocalDate(),
                            time
                    );
                },
                java.sql.Date.valueOf(target.getDate())
        );

        Reservations targetDateReservations = new Reservations(reservationList);

        targetDateReservations.validateDuplicate(target);
    }
}
