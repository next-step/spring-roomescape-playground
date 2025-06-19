package roomescape.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationApiController {
    private final AtomicLong index = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        reservations.add(new Reservation(index.getAndIncrement(), "브라운", LocalDate.now(), LocalTime.now()));
        reservations.add(new Reservation(index.getAndIncrement(), "브라운",LocalDate.now(), LocalTime.now()));
        reservations.add(new Reservation(index.getAndIncrement(), "브라운",LocalDate.now(), LocalTime.now()));

        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Void> create(@RequestBody Reservation reservation) {
        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());
        reservations.add(newReservation);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.id())).build();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(reservationItem -> Objects.equals(reservationItem.id(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }
}
