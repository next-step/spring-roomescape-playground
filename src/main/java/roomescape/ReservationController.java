package roomescape;

import java.net.URI;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);
    private final Object lock = new Object();

    public ReservationController() {
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        synchronized (lock) {
            return reservations;
        }
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> postReservation(@RequestBody ReservationRequest reservationRequest) {

        synchronized (lock) {

            // 예외처리
            if (reservationRequest.getName() == null
                    || reservationRequest.getName().trim().isBlank()
                    || reservationRequest.getDate() == null
                    || reservationRequest.getTime() == null) {
                throw new IllegalArgumentException();
            }

            Reservation reservation = new Reservation(
                    index.incrementAndGet(),
                    reservationRequest.getName(),
                    reservationRequest.getDate(),
                    reservationRequest.getTime()
            );

            reservations.add(reservation);

            return ResponseEntity.created(
                            URI.create("/reservations/" + reservation.getId()))
                    .body(reservation);
        }
    }

    @GetMapping("/reservations/{id}")
    @ResponseBody
    public Reservation getReservation(@PathVariable long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {

        synchronized (lock) {
            boolean removed = reservations.removeIf(reservation -> reservation.getId() == id);

            // 4단계
            if (!removed) {
                throw new NoSuchElementException();
            }

            return ResponseEntity.noContent().build();
        }
    }
}
