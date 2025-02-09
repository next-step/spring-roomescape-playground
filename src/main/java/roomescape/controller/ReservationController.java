package roomescape.controller;

import java.net.URI;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.entity.Reservation;
import roomescape.exception.NotFoundReservationException;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong reservationId = new AtomicLong(1);


    @GetMapping
    public List<Reservation> getReservations() {
        return reservations;
    }


    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Reservation newReservation = new Reservation(reservationId.getAndIncrement(), reservation.getName(),
                reservation.getDate(), reservation.getTime());
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId()))
                .body(newReservation);
    }

    @GetMapping("/{id}")
    public Reservation getReservationDetail(@PathVariable int id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundReservationException("Reservation with ID(" + id + ") wasn't found"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable int id) {
        boolean removed = reservations.removeIf(reservation -> reservation.getId() == id);
        deleteValidate(removed, id);
    }

    private void deleteValidate(boolean removed, int id) {
        if (!removed) {
            throw new NotFoundReservationException("Reservation with ID(" + id + ") wasn't found");
        }
    }

}
