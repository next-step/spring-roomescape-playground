package com.cholog.roomescape.roomescape.controller;

import com.cholog.roomescape.exception.BadRequestException;
import com.cholog.roomescape.roomescape.dto.request.ReservationRequest;
import com.cholog.roomescape.roomescape.dto.response.ReservationResponse;
import com.cholog.roomescape.roomescape.entity.Reservation;
import com.cholog.roomescape.roomescape.exception.notfound.TimeNotFoundException;
import com.cholog.roomescape.roomescape.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {

    private final ReservationService reservationService;

    public ReservationApiController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> response = reservationService.findAllReservations().stream()
                .map(ReservationResponse::toDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> postReservation(
            @RequestBody @Valid ReservationRequest request
    ) {
        Reservation reservation;

        try {
            reservation = reservationService.createReservation(request);
        } catch (TimeNotFoundException e) {
            throw new BadRequestException(e.getMessage());
        }

        return ResponseEntity
                .created(URI.create("/reservations/" + reservation.getId()))
                .body(ReservationResponse.toDto(reservation));
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long reservationId
    ) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.noContent().build();
    }
}
