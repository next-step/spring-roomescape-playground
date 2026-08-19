package roomescape;

import java.net.URI;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    public ReservationController() {
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        return reservations;
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> postReservation(@RequestBody ReservationRequest reservationRequest) {
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

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        reservations.removeIf(reservation -> reservation.getId() == id);
        return ResponseEntity.noContent().build();
    }
}
