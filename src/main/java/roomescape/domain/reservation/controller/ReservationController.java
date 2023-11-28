package roomescape.domain.reservation.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import roomescape.domain.reservation.dto.request.CreateReservationRequest;
import roomescape.domain.reservation.entity.Reservation;
import roomescape.domain.reservation.repository.ReservationRepositoryImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.reservation.repository.ReservationRepositoryJdbc;
import roomescape.domain.reservation.service.ReservationService;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Controller
@Validated
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("home")
    public String getHome() {
        return "home";
    }

    @GetMapping("/reservation")
    public String getReservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservationService.findAllReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody @Valid CreateReservationRequest requestDto) {
        Reservation savedReservation = reservationService.saveReservation(requestDto.getName(), requestDto.getDate(), requestDto.getTime());
        return ResponseEntity.status(CREATED)
                .location(URI.create("/reservations/" + savedReservation.getId()))
                .build();
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable @PositiveOrZero long reservationId) {
        Reservation reservation = reservationService.findReservation(reservationId);
        reservationService.deleteReservation(reservation.getId());
        return ResponseEntity.noContent().build();
    }

}
