package roomescape.controller;

import static org.springframework.http.HttpStatus.CREATED;

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
import roomescape.dto.Reservation;
import roomescape.exception.IllegalReservationException;
import roomescape.exception.NotFoundReservationException;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(0);

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        try {
            if (reservation == null || reservation.getDate() == null || reservation.getTime() == null
                    || reservation.getName().isEmpty()) {
                throw new IllegalReservationException("Reservation의 항목이 채워지지 않았습니다");
            }

            reservations.add(reservation);

            return ResponseEntity
                    .<Reservation>status(CREATED)
                    .location(java.net.URI.create("/reservations/" + reservation.getId()))
                    .body(reservation);
        } catch (IllegalReservationException e) {
            return ResponseEntity.<Reservation>status(HttpStatus.BAD_REQUEST).body(reservation);
        } catch (Exception e) {
            return ResponseEntity.<Reservation>status(HttpStatus.INTERNAL_SERVER_ERROR).body(reservation);
        }
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

        // 해당 ID의 예약이 없으면 예외 발생
        throw new NotFoundReservationException("Reservation with ID " + id + " not found");
    }


}
