package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationRequest;
import roomescape.exception.NotFoundReservationException;

@Repository
public class Reservations {
    private final List<Reservation> reservations;
    private final AtomicLong index = new AtomicLong(1);
    private final JdbcTemplate jdbcTemplate;

    public Reservations(JdbcTemplate jdbcTemplate) {
        this.reservations = new ArrayList<>();
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> readReservations() {
        return jdbcTemplate.query("SELECT * FROM reservation", (rs, rowNum) ->
            new Reservation(rs.getLong("id"),
                rs.getString("name"),
                LocalDate.parse(rs.getString("date")),
                LocalTime.parse(rs.getString("time"))
            ));
    }

    public Reservation reserve(ReservationRequest reservationRequest){
        Reservation newReservation = reservationRequest.toDomain(index.get());
        reservations.add(newReservation);
        index.incrementAndGet();
        return newReservation;
    }

    public void delete(Long id){
        Reservation deleteReservation = reservations.stream()
            .filter(it-> Objects.equals(it.getId(),id))
            .findFirst()
            .orElseThrow(() -> new NotFoundReservationException("해당 id의 예약이 존재하지 않습니다."));
        reservations.remove(deleteReservation);
    }
}
