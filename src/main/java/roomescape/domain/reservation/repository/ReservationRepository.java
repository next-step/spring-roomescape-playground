package roomescape.domain.reservation.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.domain.Reservation;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation addReservation(final Reservation reservation) {
        long id = index.getAndIncrement();
        Reservation newReservation = new Reservation(id, reservation.getName(), reservation.getDate(),
                reservation.getTime());
        reservations.add(newReservation);

        return newReservation;
    }

    public boolean removeReservation(final long id) {
        return reservations.removeIf(reservation -> reservation.getId() == id);
    }

    public List<Reservation> getReservations() {
        final String sql = "SELECT * FROM reservation";

        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) ->
            new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getObject("date", LocalDate.class),
                    resultSet.getObject("time", LocalTime.class)
            );
}
