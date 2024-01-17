package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.model.Reservation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;


@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    @GetMapping("/reservation")
    String reservation() {
        return "reservation.html";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<Reservation> read() {
        return reservations;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        if (reservation.getName() == null || reservation.getDate() == null || reservation.getTime() == null) {
            throw new InvalidReservationException();
        }
        Reservation newReservation = new Reservation(index.incrementAndGet(), reservation.getName(), reservation.getDate(), reservation.getTime());
        reservations.add(newReservation);
        return ResponseEntity
                .created(URI.create("/reservations/" + newReservation.getId()))
                .body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NotFoundReservationException::new);

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity handleException(NotFoundReservationException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity handleEmptyFieldException(InvalidReservationException e) {
        return ResponseEntity.badRequest().build();
    }
}
