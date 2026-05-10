package roomescape.domain;

import roomescape.exception.ReservationNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class Reservations {
    private final List<Reservation> reservations;

    public Reservations() {
        this.reservations = new CopyOnWriteArrayList<>(new ArrayList<>());
    }

    private Reservations(List<Reservation> reservations) {
        this.reservations = new CopyOnWriteArrayList<>(reservations);
    }

    public static Reservations from(List<Reservation> reservations) {
        return new Reservations(reservations);
    }

    public List<Reservation> getReservations() {
        return List.copyOf(reservations);
    }

    public int getReservationCount() {
        return reservations.size();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
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
