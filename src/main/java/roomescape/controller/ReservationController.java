package roomescape.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.repository.ReservationDao;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.domain.Reservation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final ReservationDao reservationDao;

    @GetMapping("/reservation")
    String reservation() {
        return "reservation.html";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        return ResponseEntity.ok(reservationDao.findAllReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        if (reservation.getName() == null || reservation.getDate() == null || reservation.getTime() == null) {
            throw new InvalidReservationException();
        }
        Long reservationId = reservationDao.insert(reservation);
        return ResponseEntity
                .created(URI.create("/reservations/" + reservationId))
                .body(reservationDao.findReservationById(reservationId));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationDao.findAllReservations().stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NotFoundReservationException::new);

        reservationDao.delete(id);

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
