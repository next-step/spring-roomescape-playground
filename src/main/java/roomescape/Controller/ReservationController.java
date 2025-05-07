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
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new InvalidReservationException("이름은 필수입니다.");
        }
        if (request.getDate() == null || request.getTime() == null) {
            throw new InvalidReservationException("날짜와 시간은 필수입니다.");
        }
        if (request.getDate().isBefore(LocalDate.now())) {
            throw new InvalidReservationException("과거 날짜로 예약할 수 없습니다.");
        }
        boolean isDuplicate = reservations.stream().anyMatch(r ->
                r.getName().equals(request.getName()) &&
                        r.getDate().equals(request.getDate()) &&
                        r.getTime().equals(request.getTime()));
        if (isDuplicate) {
            throw new InvalidReservationException("동일한 예약이 이미 존재합니다.");
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
