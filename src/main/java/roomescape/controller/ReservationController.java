package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.exception.BadRequestReservationException;
import roomescape.model.dto.ReservationDto;
import roomescape.model.entity.Time;
import roomescape.repository.ReservationRepository;
import roomescape.model.entity.Reservation;
import roomescape.repository.TimeRepository;
import roomescape.service.ReservationService;


import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(this.reservationService.findReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@Valid @RequestBody ReservationDto reservationDto) {
        Reservation reservation = this.reservationService.join(reservationDto);
        return ResponseEntity
                .created(URI.create("/reservations/" + reservation.getId()))
                .body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        this.reservationService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
