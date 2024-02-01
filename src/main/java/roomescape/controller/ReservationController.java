package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.exception.BadRequestReservationException;
import roomescape.model.dto.ReservationDto;
import roomescape.repository.ReservationRepository;
import roomescape.model.entity.Reservation;


import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(this.reservationRepository.findAll());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@Valid @RequestBody ReservationDto reservationDto) {
        Reservation reservation = this.reservationRepository.save(reservationDto.toEntity());
        return ResponseEntity
                .created(URI.create("/reservations/" + reservation.getId()))
                .body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        if (this.reservationRepository.delete(id) == 0)
            throw new BadRequestReservationException();
        return ResponseEntity.noContent().build();
    }
}
