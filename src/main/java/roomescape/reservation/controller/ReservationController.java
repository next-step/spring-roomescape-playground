package roomescape.reservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.request.ReservationRequest;
import roomescape.reservation.dto.response.ReservationResponse;
import roomescape.reservation.service.ReservationService;

import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/reservation")
    public String getReservation(){
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservations = reservationService.findReservations();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse.createReservationDto> createReservation(
            @RequestBody @Valid ReservationRequest.CreateReservationDto request,
            Errors errors
    ) {
        ReservationResponse.createReservationDto response = reservationService.saveReservation(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/reservations/" + response.id()))
                .body(response);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<?> deleteReservation(
            @PathVariable Long reservationId
    ) {
        reservationService.removeReservation(reservationId);
        return ResponseEntity.noContent().build();
    }
}
