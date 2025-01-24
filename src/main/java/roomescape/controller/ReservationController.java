package roomescape.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.entity.Reservation;
import roomescape.exception.NotFoundException;
import roomescape.exception.ReservationException;

@RestController
public class ReservationController {

    private final AtomicLong idGenerator = new AtomicLong(0);

    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservations;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        reservation.setId(idGenerator.incrementAndGet());
        reservations.add(reservation);

        URI location = URI.create("/reservations/" + reservation.getId());
        return ResponseEntity.created(location).body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        boolean removed = reservations.removeIf(reservation -> reservation.getId().equals(id));

        if (!removed) {
            throw new NotFoundException("해당 id를 가진 예약을 찾을 수 없습니다.");
        }

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<String> handleException(ReservationException e) {
        HttpStatus status = e.getHttpStatus();
        String message = e.getMessage();

        return ResponseEntity.status(status).body(message);
    }

}
