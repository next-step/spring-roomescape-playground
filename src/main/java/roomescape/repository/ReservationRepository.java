package roomescape.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {
    private JdbcTemplate jdbcTemplate;
    public ReservationRepository(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}
    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
            Reservation reservation = new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    LocalDate.parse(resultSet.getString("date")),
                    LocalTime.parse(resultSet.getString("time").substring(0, 5))
            );
            return reservation;
    };

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    public List<Reservation> findAllReservations() {
        List<Reservation> reservations1 = jdbcTemplate.query(
                "SELECT id, name, date, time FROM reservation", reservationRowMapper);
        return reservations1;
    }





    public Reservation save(Reservation reservation) {
        Reservation savedReservation = reservation.withId(index.getAndIncrement());
        reservations.add(savedReservation);
        return savedReservation;
    }

    public List<Reservation> find() {
        return reservations;
    }

    public void delete(Reservation reservation) {
        reservations.remove(reservation);
    }
}
