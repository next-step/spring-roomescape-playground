package roomescape.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import roomescape.ErrorCode;
import roomescape.NotFoundReservationException;
import roomescape.ReservationDAO;
import roomescape.domain.Reservation;

@RestController
public class ReservationController {
    private final ReservationDAO reservationDAO;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        reservationDAO = new ReservationDAO(jdbcTemplate);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        if(reservation.getName().isEmpty() || reservation.getDate() == null || reservation.getTime() == null) {
            throw new NotFoundReservationException(ErrorCode.INVALID_ARGUMENT);
        }
        Long id = reservationDAO.insert(reservation);
        Reservation newReservation = Reservation.toEntity(reservation, id);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservationDAO.findAllReservations());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {

        reservationDAO.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity handleException(NotFoundReservationException e) {
        return ResponseEntity.badRequest().build();
    }
}
