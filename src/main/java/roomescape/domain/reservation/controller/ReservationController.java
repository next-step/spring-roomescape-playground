package roomescape.domain.reservation.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.reservation.dto.request.ReservationCreateRequestDto;
import roomescape.domain.reservation.service.ReservationService;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.domain.reservation.entity.Reservation;


import java.net.URI;
import java.util.List;


@Controller
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @ResponseBody
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        List<Reservation> reservations = reservationService.getReservations();
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody @Valid ReservationCreateRequestDto requestDto) {
        if (requestDto.name() == null || requestDto.date() == null || requestDto.timeId() == null) {
            throw new InvalidReservationException();
        }
        Reservation reservation = reservationService.saveReservation(requestDto);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.id())).body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity handleException(NotFoundReservationException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity handleEmptyFieldException(InvalidReservationException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }
}
