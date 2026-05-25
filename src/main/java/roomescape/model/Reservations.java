package roomescape.model;

import org.springframework.stereotype.Repository;
import roomescape.model.errors.ReservationNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class Reservations {
    private final List<Reservation> reservations;

    public Reservations() {
        this.reservations = new ArrayList<Reservation>();
    }

    public List<Reservation> getReservationList() {
        List<Reservation> copiedReservation = new ArrayList<>();
        for (Reservation reservation : this.reservations) {
            copiedReservation.add(reservation.copy());
        }
        return List.copyOf(copiedReservation);
    }

    public void add(Reservation reservation) {
        this.reservations.add(reservation);
    }

    public void removeById(long deletingId) throws ReservationNotFoundException {
        Reservation toDelete = this.reservations.stream()
                .filter(reservation -> deletingId == reservation.id())
                .findFirst()
                .orElseThrow(ReservationNotFoundException::new);
        this.reservations.remove(toDelete);
    }
}

