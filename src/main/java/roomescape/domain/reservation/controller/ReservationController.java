package roomescape.domain.reservation.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.controller.dto.request.ReservationCreateRequestDto;
import roomescape.domain.Time;
import roomescape.repository.ReservationDao;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.domain.reservation.entity.Reservation;


import java.net.URI;
import java.util.List;


@Controller
public class ReservationController {
    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationController(ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    @ResponseBody
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        return ResponseEntity.ok(reservationDao.findAllReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody @Valid ReservationCreateRequestDto reservation) {
        if (reservation.name() == null || reservation.date() == null || reservation.timeId() == null) {
            throw new InvalidReservationException();
        }
        Long reservationId = reservationDao.insert(reservation);
        Time findTime = timeDao.findTimeById(Long.parseLong(reservation.timeId()));
        return ResponseEntity
                .created(URI.create("/reservations/" + reservationId))
                .body(reservation.toEntity(reservationId, findTime));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationDao.findAllReservations().stream()
                .filter(it -> Objects.equals(it.id(), id))
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
