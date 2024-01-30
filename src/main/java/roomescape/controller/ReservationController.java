package roomescape.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public ResponseEntity<Reservation> reserve(@RequestBody Reservation reservation) throws NotFoundReservationException {
        log.info("이전 예약자 {}", reservation);
        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());

        log.info("예약자 {}", newReservation);
        log.info("예약자 아이디 {}",newReservation.getId());

        if(newReservation.getDate() == null || newReservation.getTime() == null || newReservation.getId()==null) {
            throw new NotFoundReservationException();
        }
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> rejectReserve(@PathVariable Long id) throws NotFoundReservationException {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NotFoundReservationException::new);

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleException(Exception e) {
        return ResponseEntity.badRequest().build();
    }
}
