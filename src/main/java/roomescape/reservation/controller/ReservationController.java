package roomescape.reservation.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.service.ReservationService;

@Controller
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> create(@Valid @RequestBody ReservationRequest reservationRequest) {
        ReservationResponse response = reservationService.createReservation(reservationRequest);
        return ResponseEntity.created(URI.create("/reservations/" + response.id())).body(response);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> read() {
        return ResponseEntity.ok(reservationService.readAllReservations());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
