package roomescape.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.NotFoundReservationException;
import roomescape.domain.Reservation;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        if(reservation.getName().isEmpty() || reservation.getDate().isEmpty() || reservation.getTime().isEmpty()) {
            throw new NotFoundReservationException();
        }
        Reservation newReservation = new Reservation(index.getAndIncrement(), reservation.getName(), reservation.getDate(), reservation.getTime());
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        Reservation reservationToDelete = reservations.stream()
                .filter(reservation -> Objects.equals(reservation.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NotFoundReservationException());
        reservations.remove(reservationToDelete);
        updateReservationsId();
        return ResponseEntity.noContent().build();
    }

    private void updateReservationsId() {
        index.set(1);
        reservations.forEach(reservation -> reservation.updateId(index.getAndIncrement()));
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity handleException(NotFoundReservationException e) {
        return ResponseEntity.badRequest().build();
    }
}
