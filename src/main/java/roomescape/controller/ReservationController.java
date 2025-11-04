// ReservationController.java
package roomescape.controller;

import java.util.ArrayList;
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

import roomescape.model.Reservation;
import roomescape.exception.BadRequestReservationException;

@RestController // RestController로 변경
@RequestMapping("/reservations")
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    public void reset() {
        reservations.clear();
        index.set(0);
    }

    @GetMapping
    public List<Reservation> getReservation() {
        return reservations;
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation newReservation) {
        // 예외 조건 추가
        if (newReservation.getName() == null || newReservation.getName().isBlank() ||
            newReservation.getDate() == null || newReservation.getDate().isBlank() ||
            newReservation.getTime() == null || newReservation.getTime().isBlank()) {
            throw new BadRequestReservationException("Required fields are missing.");
        }
      
        long id = index.incrementAndGet();
        Reservation reservation = new Reservation(
                id,
                newReservation.getName(),
                newReservation.getDate(),
                newReservation.getTime()
        );
        reservations.add(reservation);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/reservations/" + id)
                .body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        boolean removed = reservations.removeIf(r -> r.getId().equals(id));
        if (removed) {
          return ResponseEntity.noContent().build();
        } else {
          throw new BadRequestReservationException("Reservation not found.");
        }
    }
}
