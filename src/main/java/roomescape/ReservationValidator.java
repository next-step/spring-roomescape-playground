package roomescape;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import roomescape.exception.ReservationConflictException;
import java.util.List;

@Component
public class ReservationValidator {

    private final JdbcTemplate jdbcTemplate;

    public ReservationValidator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void validateDuplicate(Reservation target) {
        List<Reservation> reservationList = jdbcTemplate.query(
                "SELECT id, name, date, time FROM reservation WHERE date = ?",
                (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime()
                ),
                java.sql.Date.valueOf(target.getDate())
        );

        Reservations targetDateReservations = new Reservations(reservationList);

        targetDateReservations.validateDuplicate(target);
    }
}
