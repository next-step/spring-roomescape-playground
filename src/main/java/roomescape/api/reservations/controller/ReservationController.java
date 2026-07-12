package roomescape.api.reservations.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.api.reservations.dto.request.ReservationRegisterRequest;
import roomescape.api.reservations.dto.response.ReservationResponse;
import roomescape.api.reservations.service.ReservationsService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationsService reservationsService;

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(reservationsService.getReservations());
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody @Valid final ReservationRegisterRequest reservationRegisterRequest
    ) {

        final ReservationResponse reservationResponse = reservationsService.createReservations(
                reservationRegisterRequest.name(),
                reservationRegisterRequest.date(),
                reservationRegisterRequest.timeId()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/reservations/" + reservationResponse.id())
                .body(reservationResponse);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable(name = "reservationId") final long reservationId
    ) {
        reservationsService.deleteReservations(reservationId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(null);
    }
}
