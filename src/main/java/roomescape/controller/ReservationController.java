package roomescape.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.valid.ErrorCode;
import roomescape.valid.IllegalReservationException;
import roomescape.valid.NotFoundReservationException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RestController
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservations")
    public List<Reservation> reservations() {
        return reservations;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> reserve(@Valid @RequestBody Reservation reservation, BindingResult bindingResult) throws IllegalReservationException {
        log.info("이전 예약자 {}", reservation);
        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());

        log.info("예약자 {}", newReservation);
        log.info("예약자 아이디 {}",newReservation.getId());

        if(bindingResult.hasErrors()) {
            throw new IllegalReservationException(ErrorCode.ILLEGAL_ARGUMENT);
        }

        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> rejectReserve(@PathVariable Long id) throws NotFoundReservationException {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NotFoundReservationException(ErrorCode.RESERVATION_NO_FOUND));

        reservations.remove(reservation);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({NotFoundReservationException.class, IllegalReservationException.class})
    public ResponseEntity<Void> handleException(Exception e) {
        log.info("error message = {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    }
}
