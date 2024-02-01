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


import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    @Autowired
    public ReservationController(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(this.reservationRepository.findAll());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@Valid @RequestBody ReservationDto reservationDto) {
        Time time = this.timeRepository.findById(reservationDto.timeId());
        Reservation reservation = this.reservationRepository.save(reservationDto.toEntity(time));
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
