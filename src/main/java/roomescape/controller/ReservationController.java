package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final Reservations reservations = new Reservations();

    @GetMapping
    public List<Reservation> showReservations() {
        return reservations.getAll();
    }

    @PostMapping
    public ResponseEntity<Reservation> add(@RequestBody Reservation reservation) {
        Reservation saved = reservations.add(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + saved.getId()))
                .body(saved);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        reservations.cancel(id);
        return ResponseEntity.noContent().build();
    }
}