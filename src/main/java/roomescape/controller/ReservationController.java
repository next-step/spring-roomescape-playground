package roomescape.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationDao;
import roomescape.valid.ErrorCode;
import roomescape.valid.IllegalReservationException;
import roomescape.valid.NotFoundReservationException;

import javax.sql.DataSource;
import java.net.URI;
import java.util.*;

@Slf4j
@RestController
public class ReservationController {
    private final ReservationDao reservationRepository;

    public ReservationController(DataSource dataSource) {
        reservationRepository = new ReservationDao(dataSource);
    }

    @GetMapping("/reservations")
    public List<Reservation> reservations() {
        return reservationRepository.findAllReservation();
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> reserve(@Valid @RequestBody Reservation reservation,
                                               BindingResult bindingResult) throws IllegalReservationException {
        if(bindingResult.hasErrors()) {
            throw new IllegalReservationException(ErrorCode.ILLEGAL_ARGUMENT);
        }

        Long id = reservationRepository.save(reservation);
        Reservation newReservation = Reservation.toEntity(reservation, id);
        return ResponseEntity.created(URI.create("/reservations/" + id)).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> rejectReserve(@PathVariable Long id) throws NotFoundReservationException {
        reservationRepository.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({NotFoundReservationException.class, IllegalReservationException.class})
    public ResponseEntity<Void> handleException(Exception e) {
        log.info("error message = {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    }
}
