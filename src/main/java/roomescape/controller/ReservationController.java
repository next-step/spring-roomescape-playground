package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundReservationException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public ReservationController() {
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        return reservations;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> add(@RequestBody Reservation reservation) {
        if (reservation.getName().isBlank() || reservation.getDate() == null || reservation.getTime() == null) {
            throw new IllegalArgumentException("name, date, time은 필수입니다.");
        }

        Reservation newReservation = reservation.toEntity(index.getAndIncrement());
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        Reservation removeTarget = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NotFoundReservationException::new);

        reservations.remove(removeTarget);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({NotFoundReservationException.class, IllegalArgumentException.class})
    public ResponseEntity<Void> handleException() {
        return ResponseEntity.badRequest().build();
    }
}