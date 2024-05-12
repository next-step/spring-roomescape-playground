package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class RoomescapeController {
    private AtomicLong id = new AtomicLong(1);
    List<Reservation> reservations = new ArrayList<Reservation>();

    @GetMapping("/")
    public String welcomePage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> checkReservations() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Reservation newReservation = new Reservation(id.getAndIncrement(), reservation.getName(), reservation.getDate(), reservation.getTime());
        reservations.add(newReservation);
        URI location = URI.create("/reservations/" + newReservation.getId());
        return ResponseEntity.created(location).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }
}
