package roomescape;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservation")
    public String reservation(Model model) {
        if (reservations.isEmpty()) {
            reservations.add(new Reservation(index.getAndIncrement(), "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0, 0)));
            reservations.add(new Reservation(index.getAndIncrement(), "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 0, 0)));
            reservations.add(new Reservation(index.getAndIncrement(), "브라운", LocalDate.of(2023, 1, 3), LocalTime.of(12, 0, 0)));
        }

        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        return ResponseEntity.status(HttpStatus.OK).body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation){
        if (reservation.getName() == null || reservation.getDate() == null || reservation.getTime() == null) {
            throw new NotFoundReservationException("빈 값이 존재합니다!");
        }
        if (reservations.stream().anyMatch(r -> Objects.equals(r.getName(), reservation.getName()))) {
            throw new NotFoundReservationException("이미 존재하는 id 입니다!");
        }

        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());
        reservations.add(newReservation);

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/reservations/" + newReservation.getId()))
                .body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NotFoundReservationException("id를 찾을 수 없습니다!"));

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<String> handleException(NotFoundReservationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    public static class NotFoundReservationException extends RuntimeException {
        public NotFoundReservationException(String message) {
            super(message);
        }
    }
}