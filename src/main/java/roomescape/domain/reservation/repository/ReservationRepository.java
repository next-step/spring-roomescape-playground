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
import roomescape.domain.reservation.dto.ReservationRequest;

@Repository
public class ReservationRepository {

    private static final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) ->
            new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getObject("date", LocalDate.class),
                    resultSet.getObject("time", LocalTime.class)
            );

    private final JdbcTemplate jdbcTemplate;
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);


    public ReservationRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation addReservation(final ReservationRequest ReservationRequest) {
        long id = index.getAndIncrement();
        Reservation newReservation = new Reservation(id, ReservationRequest.name(), ReservationRequest.date(),
                ReservationRequest.time());
        reservations.add(newReservation);

        return newReservation;
    }

    public boolean removeReservation(final long id) {
        return reservations.removeIf(reservation -> reservation.getId() == id);
    }

    public List<Reservation> getReservations() {
        String sql = "SELECT * FROM reservation";

        return jdbcTemplate.query(sql, reservationRowMapper);
    }
}
