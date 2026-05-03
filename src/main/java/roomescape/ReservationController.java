package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getAllBookings() {
        return ResponseEntity.ok().body(this.reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        int newIndex = (int) this.index.getAndIncrement();
        Reservation newReservation = new Reservation(newIndex, reservation.getName(), reservation.getDate(), reservation.getTime());
        this.reservations.add(newReservation);

        return ResponseEntity
                .created(URI.create("/reservations/" + newIndex))
                .body(newReservation);
    }

    @DeleteMapping("/reservations/{deletingIndex}")
    public ResponseEntity<Void> deleteReseravation(@PathVariable Integer deletingIndex) {
        Reservation toDelete = this.reservations.stream()
                .filter(reservation -> deletingIndex == reservation.getId())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Wrong Index"));

        this.reservations.remove(toDelete);
        return ResponseEntity.noContent().build();
    }
}
