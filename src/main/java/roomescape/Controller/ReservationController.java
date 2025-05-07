package roomescape.Controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.Domain.Reservation;
import roomescape.InvalidReservationException;
import roomescape.NotFoundReservationException;

@RestController
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> add(@RequestBody Reservation request) {
        if (request == null) {
            throw new InvalidReservationException("요청이 null입니다");
        }
        if (request.getName() == null || request.getName().isBlank()) {
            throw new InvalidReservationException("이름이 비어있습니다.");
        }
        if (request.getDate() == null) {
            throw new InvalidReservationException("날짜가 비어있습니다.");
        }
        if (request.getTime() == null) {
            throw new InvalidReservationException("시간이 비어있습니다.");
        }

        Reservation newReservation = new Reservation(
                (int) index.getAndIncrement(),
                request.getName(),
                request.getDate(),
                request.getTime()
        );
        reservations.add(newReservation);
        return ResponseEntity
                .created(URI.create("/reservations/" + newReservation.getId()))
                .body(newReservation);
    }

    @GetMapping("/reservations")
    public List<Reservation> findAll() {
        return reservations;
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean removed = reservations.removeIf(r -> r.getId() == id);
        if (!removed) {
            throw new NotFoundReservationException("해당 ID가 없습니다.");
        }
        return ResponseEntity.noContent().build();
    }
}
