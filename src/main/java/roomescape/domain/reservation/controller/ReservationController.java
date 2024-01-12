package roomescape.domain.reservation.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.reservation.dto.request.ReservationCreateRequestDto;
import roomescape.domain.reservation.entity.Reservation;
import roomescape.exception.custom.BusinessException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static roomescape.exception.ErrorCode.RESERVATION_NOT_FOUND;

@Controller
public class ReservationController {
    private AtomicLong index = new AtomicLong(0);
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> readReservations() {
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody @Valid ReservationCreateRequestDto requestDto) {
        Reservation newReservation = requestDto.toEntity(index.incrementAndGet(),
                requestDto.name(), requestDto.date(), requestDto.time());
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        Reservation deleteReservation = reservations.stream()
                .filter(reservation -> reservation.getId().equals(reservationId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(RESERVATION_NOT_FOUND));
        reservations.remove(deleteReservation);
        return ResponseEntity.noContent().build();
    }
}