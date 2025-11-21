package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservations(@Valid @RequestBody ReservationRequest request) {
        Reservation newReservation = reservationService.registerReservation(request.name(), request.date(), request.time());

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).
                body(ReservationResponse.from(newReservation));
    }


    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> readReservations() {
        List<Reservation> reservations = reservationService.getReservations();

        List<ReservationResponse> responses = reservations.stream().
                map(ReservationResponse::from).toList();

        return ResponseEntity
                .ok()
                .body(responses);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservations(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();

    }
}
