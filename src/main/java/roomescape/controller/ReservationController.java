package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.reservation.ReservationRequest;
import roomescape.domain.reservation.Reservation;
import roomescape.service.ReservationService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        return ResponseEntity.ok(reservationService.read());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@Valid @RequestBody ReservationRequest request) {
        Reservation newReservation = reservationService.create(request);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
