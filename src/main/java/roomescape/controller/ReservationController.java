package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {

        List<ReservationResponse> reservationResponses = reservationService.
                getReservations().stream().map(ReservationResponse::convert).toList();;

        return ResponseEntity.ok().body(reservationResponses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> postReservation(
            @Valid @RequestBody ReservationRequest reservationRequest
    ) {

        Reservation reservation = reservationService.createReservation(
                reservationRequest.getName(),
                reservationRequest.getDate(),
                reservationRequest.getTime()
        );

        return ResponseEntity.created(
                        URI.create("/reservations/" + reservation.getId()))
                .body(ReservationResponse.convert(reservation));
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationResponse> getReservation(
            @PathVariable long id
    ) {

        Reservation reservation =
                reservationService.getReservation(id);

        return ResponseEntity.ok().body(ReservationResponse.convert(reservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable long id
    ) {

        reservationService.deleteReservation(id);

        return ResponseEntity.noContent().build();
    }
}
