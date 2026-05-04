package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.exceptions.ReservationNotFoundException;

public class Reservations {
    private final List<Reservation> reservations;
    private final AtomicLong idCounter = new AtomicLong(1);

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public void add(Reservation reservation) {
        reservations.add(reservation);
    }

    public void delete(Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(ReservationNotFoundException::new);
        reservations.remove(reservation);
    }

    public Long nextId() {
        return idCounter.getAndIncrement();
    }

    public List<Reservation> get() {
        return new ArrayList<>(reservations);
    }
}
