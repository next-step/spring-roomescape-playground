package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private final AtomicLong index = new AtomicLong(0);
    private final List<Reservation> reservations = new ArrayList<>();

    public ReservationController() {
        reservations.add(new Reservation(index.incrementAndGet(), "브라운", "2023-01-01", "10:00"));
        reservations.add(new Reservation(index.incrementAndGet(), "브라운", "2023-01-02", "11:00"));
        reservations.add(new Reservation(index.incrementAndGet(), "브라운", "2023-01-03", "12:00"));
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        return reservations;
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequest request) {
        Reservation reservation = new Reservation(
                index.incrementAndGet(),
                request.getName(),
                request.getDate(),
                request.getTime()
        );
        reservations.add(reservation);

        return ResponseEntity
                .created(URI.create("/reservations/" + reservation.getId()))
                .body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
        return ResponseEntity.noContent().build();
    }
}
