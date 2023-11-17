package roomescape.controller;

import static roomescape.utils.ErrorMessage.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.util.StringUtils;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.Reservation;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);


    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        validate(reservation);

        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());
        reservations.add(newReservation);
        return ResponseEntity
                .created(URI.create("/reservations/" + newReservation.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(newReservation);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> readReservation() {
        return ResponseEntity.ok().body(reservations);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NotFoundReservationException(NON_EXISTING_RESERVATION));

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }

    private void validate(Reservation reservation){
        if (!StringUtils.hasText(reservation.getName())) {
            throw new NotFoundReservationException(EMPTY_NAME_ERROR);
        }
        if (!StringUtils.hasText(reservation.getDate())) {
            throw new NotFoundReservationException(EMPTY_DATE_ERROR);
        }
        if (!StringUtils.hasText(reservation.getTime())) {
            throw new NotFoundReservationException(EMPTY_TIME_ERROR);
        }
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity handleException(NotFoundReservationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
