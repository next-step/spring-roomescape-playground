package roomescape.reservation.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.service.ReservationService;

import javax.net.ssl.SSLEngineResult;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationApiController {

    private final ReservationService reservationService;

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> reservations() {
        List<ReservationResponse> responseList = reservationService.findAllReservations();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseList);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(
            @Valid
            @RequestBody ReservationRequest reservationRequest
    ) {

        ReservationResponse response = reservationService.addReservation(reservationRequest);

        return ResponseEntity.created(URI.create("/reservations/" + response.getId()))
                .body(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long id
    ) {
        reservationService.cancelReservation(id);

        return ResponseEntity.noContent()
                .build();
    }
}
