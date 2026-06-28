package roomescape.reservation.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.service.ReservationService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationResponse> getReservations() {
        return reservationService.getAllReservations().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest request) {
        Reservation newReservation = reservationService.createReservation(request);
        return ResponseEntity
                .created(URI.create("/reservations/" + newReservation.getId()))
                .body(ReservationResponse.from(newReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
