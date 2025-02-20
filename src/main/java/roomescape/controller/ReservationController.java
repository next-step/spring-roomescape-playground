package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.entity.dto.ReservationCreateDto;
import roomescape.entity.Reservation;
import roomescape.exception.ReservationException;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> getReservations() {
        return reservationService.findAllReservations();
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationCreateDto reservationCreateDto) {
        final Reservation reservation = reservationService.saveReservation(reservationCreateDto);
        URI location = URI.create("/reservations/" + reservation.getId());
        return ResponseEntity.created(location).body(reservation);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservationById(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<String> handleException(ReservationException e) {
        HttpStatus status = e.getHttpStatus();
        String message = e.getMessage();

        return ResponseEntity.status(status).body(message);
    }
}
