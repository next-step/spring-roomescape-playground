package roomescape.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundReservationException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

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
    public ResponseEntity<Reservation> addReservation(
            @RequestBody Reservation request) {
        if (request.getName() == null || request.getName().isBlank() ||
                request.getDate() == null || request.getDate().isBlank() ||
                request.getTime() == null || request.getTime().isBlank()) {

            throw new IllegalArgumentException("필수 인자가 누락되었습니다.");
        }
        Long newId = index.incrementAndGet();

        Reservation newReservation = new Reservation(
                newId,
                request.getName(),
                request.getDate(),
                request.getTime()
        );

        reservations.add(newReservation);

        return ResponseEntity.created(URI.create("/reservations/" + newId)).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id){
        boolean isRemoved = reservations.removeIf(reservation -> reservation.getId().equals(id));
        if(!isRemoved){
            throw new NotFoundReservationException();
        }
        return ResponseEntity.noContent().build();
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }
    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleNotFound(NotFoundReservationException e) {
        return ResponseEntity.notFound().build();
    }
}
