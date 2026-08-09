package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reservations {
    private final List<Reservation> reservations;

    public Reservations() {
        this.reservations = new ArrayList<>();
        createReservations();
    }

    public List<Reservation> readReservations() {
        return Collections.unmodifiableList(reservations);
    }

    private void createReservations() {
        reservations.add(new Reservation(1L, "브라운", LocalDate.of(2026, 1, 1), LocalTime.of(10, 0)));
        reservations.add(new Reservation(2L, "브라운", LocalDate.of(2026, 2, 1), LocalTime.of(11, 0)));
        reservations.add(new Reservation(3L, "브라운", LocalDate.of(2026, 3, 1), LocalTime.of(12, 0)));
    }
}
