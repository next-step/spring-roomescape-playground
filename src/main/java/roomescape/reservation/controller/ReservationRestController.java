package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.service.ReservationService;

import java.net.URI;
import java.util.List;

@RestController
public class ReservationRestController {
    private final ReservationService reservationService;

    public ReservationRestController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> read() {
        List<ReservationResponse> response = reservationService.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest request) {
        Reservation newReservation = reservationService.save(request);

        URI location = URI.create("/reservations/" + newReservation.id());
        return ResponseEntity
                .created(location)
                .body(ReservationResponse.from(newReservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
