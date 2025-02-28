package roomescape.domain.reservation.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.reservation.dto.ReservationRequest;
import roomescape.domain.reservation.dto.ReservationResponse;
import roomescape.domain.reservation.service.ReservationService;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> reservationResponses = reservationService.getReservationResponses();

        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody @Valid final ReservationRequest reservationRequest) {
        ReservationResponse result = reservationService.createReservation(reservationRequest);
        URI Location = URI.create("/reservations/" + result.id());

        return ResponseEntity.created(Location).body(result);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity deleteReservation(@PathVariable final long reservationId) {
        reservationService.deleteReservation(reservationId);

        return ResponseEntity.noContent().build();
    }
}
