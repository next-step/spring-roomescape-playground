package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import roomescape.exceptions.EmptyNameException;
import roomescape.exceptions.PastDateTimeException;
import roomescape.exceptions.ReservationNotFoundException;

public class Reservations {
    private final List<Reservation> reservations;

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public void add(Reservation reservation) {
        if (reservation.getName().isBlank()) {
            throw new EmptyNameException();
        }
        if (reservation.isPast()) {
            throw new PastDateTimeException();
        }
        reservations.add(reservation);
    }

    public void delete(Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(ReservationNotFoundException::new);
        reservations.remove(reservation);
    }

    public List<Reservation> get() {
        return new ArrayList<>(reservations);
    }
}
