package roomescape;

import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();

    public ReservationRepository() {
        reservations.add(
                new Reservation(1L, "브라운", "2026-08-12", "10:00")
        );

        reservations.add(
                new Reservation(2L, "브라운", "2026-08-13", "11:00")
        );

        reservations.add(
                new Reservation(3L, "브라운", "2026-08-14", "12:00")
        );
    }

    public List<Reservation> findAll() {
        return reservations;
    }
}
