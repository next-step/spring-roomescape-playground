package roomescape.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import roomescape.dto.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(0);

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation,
                                                         UriComponentsBuilder uriBuilder) {
        reservation.setId(counter.incrementAndGet());
        reservation.setDate(reservation.getDate());
        reservation.setTime(reservation.getTime());
        reservations.add(reservation);

        // "Location" 헤더 설정
        return ResponseEntity.status(CREATED)
                .location(java.net.URI.create("/reservations/" + reservation.getId()))
                .body(reservation);
    }


    @GetMapping
    public List<Reservation> getReservation() {
        return reservations;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable long id) {
        Iterator<Reservation> iterator = reservations.iterator();
        while (iterator.hasNext()) {
            Reservation reservation = iterator.next();
            if (reservation.getId() == id) {
                iterator.remove();
                counter.decrementAndGet();
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.notFound().build();
    }


}
