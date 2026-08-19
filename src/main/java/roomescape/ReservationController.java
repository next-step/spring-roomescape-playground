package roomescape;

import java.net.URI;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

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
    public ResponseEntity<Reservation> postReservations(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(
                index.incrementAndGet(),
                reservationRequest.getDate(),
                reservationRequest.getName(),
                reservationRequest.getTime()
        );

        reservations.add(reservation);

        return ResponseEntity.created(
                URI.create("/reservations/" + reservation.getId()))
                .body(reservation);
    }
}
