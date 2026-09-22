package roomescape.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;

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
    public ResponseEntity<Void> deleteReservation(
            @PathVariable long id
    ){
        reservations.removeIf(reservation -> reservation.getId().equals(id));
        return ResponseEntity.noContent().build();
    }
}
