package roomescape.domain;

import org.springframework.stereotype.Component;
import roomescape.exception.ReservationNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class Reservations {

    private final List<Reservation> reservations;
    private final AtomicLong index;

    public Reservations() {
        this.reservations = new CopyOnWriteArrayList<>(new ArrayList<>());
        index = new AtomicLong(reservations.size() + 1);
    }

    public List<Reservation> getReservations() {
        return List.copyOf(reservations);
    }

    public Reservation addReservation(Reservation validatedReservation) {
        Long id = index.getAndIncrement();
        Reservation reservation = Reservation.withId(validatedReservation, id);
        this.reservations.add(reservation);

        return reservation;
    }

    public void deleteReservationById(Long id) {
        Reservation reservation = findById(id);
        reservations.remove(reservation);
    }

    private Reservation findById(Long id) {
        return reservations.stream()
                .filter(reservation -> Objects.equals(reservation.getId(), id))
                .findFirst()
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }
}
