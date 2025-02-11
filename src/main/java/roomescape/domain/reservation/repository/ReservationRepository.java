package roomescape.domain.reservation.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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
        final String sql = "select * from reservation";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Reservation.class));
    }
}
