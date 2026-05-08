package roomescape;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    private final List<Reservation> reservations = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong index = new AtomicLong(1);

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        boolean isDuplicate = reservations.stream()
                .anyMatch(r -> r.hasSameDateTimeWith(reservation));

        if (isDuplicate) {
            throw new DuplicateReservationException(reservation.getDate(), reservation.getTime());
        }

        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());
        reservations.add(newReservation);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation.Response>> readAll() {
        List<Reservation.Response> responseList = reservations.stream()
                .map(Reservation.Response::new)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation.Response> readOne(@PathVariable Long id) {
        return reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .map(Reservation.Response::new)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundReservationException(id));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NotFoundReservationException(id));

        reservations.remove(reservation);
        return ResponseEntity.noContent().build();
    }
}