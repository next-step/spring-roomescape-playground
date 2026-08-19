package roomescape;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.exception.NotFoundException;

@Controller
public class ReservationController {

    private AtomicLong index = new AtomicLong(0);

    private List<Reservation> reservations = new ArrayList<>();

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException() {
        return ResponseEntity.badRequest().body("잘못된 요청입니다");
    }

    @GetMapping("/reservation")
    public String adminPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Reservation newReservaion = Reservation.toEntity(reservation, index.incrementAndGet());
        boolean hasEmpty = Stream.of(
            reservation.getName(),
            reservation.getDate(),
            reservation.getTime()
        ).anyMatch(value -> value == null || value.isBlank());

        if(hasEmpty){
            throw new IllegalArgumentException();
        }

        reservations.add(newReservaion);

        return ResponseEntity
            .created(
                URI.create("/reservations/" + newReservaion.getId())) //201 Created, /reservations/1
            .body(newReservaion);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        Reservation reservation = reservations.stream()
            .filter(it -> Objects.equals(it.getId(), id))
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Reservation not found: id=" + id));

        reservations.remove(reservation);

        return ResponseEntity.noContent().build(); //204
    }
}
