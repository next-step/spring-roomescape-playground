package roomescape.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("reservations")
    public ResponseEntity<List<Reservation>> readAll() {
        return ResponseEntity.ok().body(reservationService.findAllReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@Valid @RequestBody ReservationRequestDto requestDto) {
        Reservation newReservation = reservationService.createReservation(requestDto);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.deleteReservation(id);

        return ResponseEntity.noContent().build();
    }
}
