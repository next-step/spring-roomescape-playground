package roomescape.model;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

    public void removeById(long deletingId) {
        Reservation toDelete = this.reservations.stream()
                .filter(reservation -> deletingId == reservation.id())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Wrong Index"));
        this.reservations.remove(toDelete);
    }
}

