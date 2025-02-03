package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {

    private List<Reservation> reservations = createReservations();

    public List<Reservation> getReservations() {
        return reservations;
    }

    private List<Reservation> createReservations() {
        return List.of(
                createReservation(1L, "브라운", LocalDate.of(2024, 2, 1), LocalTime.of(10, 0, 0)),
                createReservation(2L, "브라운", LocalDate.of(2024, 2, 2), LocalTime.of(11, 0, 0)),
                createReservation(3L, "브라운", LocalDate.of(2024, 2, 3), LocalTime.of(12, 0, 0))
        );
    }

    private Reservation createReservation(Long id, String name, LocalDate date, LocalTime time) {
        return new Reservation(id, name, date, time);
    }
}
