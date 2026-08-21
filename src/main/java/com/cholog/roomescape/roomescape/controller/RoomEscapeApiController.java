package com.cholog.roomescape.roomescape.controller;

import com.cholog.roomescape.roomescape.dto.request.ReservationRequest;
import com.cholog.roomescape.roomescape.dto.response.ReservationResponse;
import com.cholog.roomescape.roomescape.entity.Reservation;
import com.cholog.roomescape.roomescape.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class RoomEscapeApiController {

    private final ReservationService reservationService;

    public RoomEscapeApiController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @ResponseBody
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> response = reservationService.findAllReservations().stream()
                .map(ReservationResponse::toDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    @ResponseBody
    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> postReservation(
            @RequestBody @Valid ReservationRequest request
    ) {
        Reservation reservation = reservationService.createReservation(request);

        return ResponseEntity
                .created(URI.create("/reservations/" + reservation.getId()))
                .body(ReservationResponse.toDto(reservation));
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long reservationId
    ) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.noContent().build();
    }
}
