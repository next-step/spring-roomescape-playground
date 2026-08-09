package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationRepository {
    private final List<Reservation> reservations = createReservations();

    private List<Reservation> createReservations() {
        List<Reservation> reservations = new ArrayList<>();

        reservations.add(new Reservation(
                1,
                "예약자1",
                LocalDate.of(2026, 8, 4),
                LocalTime.of(10, 0)
        ));
        reservations.add(new Reservation(
                2,
                "예약자2",
                LocalDate.of(2026, 8, 5),
                LocalTime.of(10, 10)
        ));
        reservations.add(new Reservation(
                3,
                "예약자3",
                LocalDate.of(2026, 8, 6),
                LocalTime.of(10, 20)
        ));

        return reservations;
    }

    public List<Reservation> getReservations() {
        return List.copyOf(reservations);
    }
}
