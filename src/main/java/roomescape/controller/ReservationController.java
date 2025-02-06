package roomescape.controller;

import java.net.URI;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.entity.Reservation;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.validator.ReservationValidator;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);


    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations() {
        if (reservations.isEmpty()) {
            throw new NotFoundReservationException("Reservations aren't here");
        }
        return ResponseEntity.ok(reservations);
    }


    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        ReservationValidator.validate(reservation);
        Reservation newReservation = new Reservation(index.getAndIncrement(), reservation.getName(),
                reservation.getDate(), reservation.getTime());
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId()))
                .body(newReservation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationDetail(@PathVariable int id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable int id) {
        boolean removed = reservations.removeIf(reservation -> reservation.getId() == id);
        ReservationValidator.deleteValidate(removed, id);
        return ResponseEntity.noContent().build();
    }

}
