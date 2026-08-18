package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationRepository {
    private final List<Reservation> reservations = List.of(
            new Reservation(1L, "브라운", LocalDate.now().plusDays(1), LocalTime.of(10, 0)),
            new Reservation(2L, "브라운", LocalDate.now().plusDays(2), LocalTime.of(11, 0)),
            new Reservation(3L, "브라운", LocalDate.now().plusDays(3), LocalTime.of(12, 0))
    );

    public List<Reservation> findAll() {
        return reservations;
    }
}
