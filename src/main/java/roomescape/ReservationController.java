package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservations;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Reservation newReservation = new Reservation(
                index.incrementAndGet(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
        reservations.add(newReservation);

        return ResponseEntity
                .created(URI.create("/reservations/" + newReservation.getId()))
                .body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));

        return ResponseEntity.noContent().build();
    }
}
