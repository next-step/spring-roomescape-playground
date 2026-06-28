package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.dto.TimeRequest;
import roomescape.exception.BadRequestException;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.ReservationRepository;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@RestController
public class ApiController {

    private final ReservationService reservationService;

    public ApiController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservationService.findReservations();
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody @Valid ReservationRequest request) {
        Reservation reservation = reservationService.createReservation(request);
        return ResponseEntity
                .created(URI.create("/reservations/" + reservation.getId()))
                .body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.removeReservation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/times")
    public List<Time> getTimes() {
        return reservationService.findTimes();
    }

    @PostMapping("/times")
    public ResponseEntity<Time> createTime(@RequestBody @Valid TimeRequest request) {
        Time time = reservationService.createTime(request);
        return ResponseEntity
                .created(URI.create("/times/" + time.getId()))
                .body(time);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        reservationService.removeTime(id);
        return ResponseEntity.noContent().build();
    }


}
