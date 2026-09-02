package roomescape;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundException;

@Controller
public class ReservationController {

    private final AtomicLong index = new AtomicLong(0);

    private List<Reservation> reservations = new ArrayList<>();

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Void> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity<Void> handleInvalidReservationException() {
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/reservation")
    public String adminPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(
        @RequestBody ReservationRequest reservationRequest) {
        reservationRequest.validate();
        Reservation newReservation = Reservation.create(
            index.incrementAndGet(),
            reservationRequest.name(),
            reservationRequest.date(),
            reservationRequest.time()
        );

        reservations.add(newReservation);

        return ResponseEntity
            .created(URI.create("/reservations/" + newReservation.getId()))
            .body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        Reservation reservation = reservations.stream()
            .filter(it -> Objects.equals(it.getId(), id))
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Reservation not found: id=" + id));

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }
}
