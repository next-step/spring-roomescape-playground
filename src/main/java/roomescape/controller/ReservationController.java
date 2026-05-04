package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.ReservationRequest;
import roomescape.model.Reservation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> reservations(@RequestBody ReservationRequest reservationRequest) {
        long id = index.getAndIncrement();

        Reservation reservation = new Reservation(
                id,
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time()
        );

        reservations.add(reservation);

        return ResponseEntity
                .created(URI.create("/reservations/" + id))
                .body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> reservations(@PathVariable Long id) {

        reservations.removeIf(reservation -> reservation.id().equals(id));

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> reservations() {
        return reservations;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }
}
