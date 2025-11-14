package roomescape;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


@RestController
@RequestMapping("/reservations")
public class ReservationAPIController {
    private final List<Reservation> reservations=new ArrayList<>();
    private final AtomicLong idCounter=new AtomicLong(1);

    @GetMapping
    public List<Reservation> getReservations() {
        return reservations;
    }
    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody Map<String, String> params) {
        Reservation reservation = new Reservation(
            idCounter.getAndIncrement(),
            params.get("name"),
            params.get("date"),
            params.get("time")
        );
        reservations.add(reservation);
        return ResponseEntity.status(HttpStatus.CREATED)
            .header("Location", "/reservations/" + reservation.getId())
            .body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservations.removeIf(r -> r.getId().equals(id));
        return ResponseEntity.noContent().build();
    }
}
