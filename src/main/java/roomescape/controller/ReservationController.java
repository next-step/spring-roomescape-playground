package roomescape.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.controller.dto.CreateReservation;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;

@Controller
public class ReservationController {

    private final AtomicLong id = new AtomicLong();
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation.html";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponse> reservations() {
        return reservations.stream()
            .map(ReservationResponse::from)
            .toList();
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(CreateReservation request) {
        Reservation reservation = request.toReservation(id.incrementAndGet());
        reservations.add(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
            .filter(it -> it.getId() == id)
            .findAny()
            .orElseGet(null);

        reservations.remove(reservation);
        return ResponseEntity.noContent().build();
    }
}
