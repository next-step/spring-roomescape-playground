package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundReservationException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservationRequest) {
        Reservation reservation = new Reservation(
                index.incrementAndGet(),
                reservationRequest.getName(),
                reservationRequest.getDate(),
                reservationRequest.getTime()
        );

        reservations.add(reservation);

        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId()))
                .body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        boolean removed = reservations.removeIf(reservation -> reservation.getId().equals(id));
        if (!removed) {
            throw new NotFoundReservationException("존재하지 않는 예약을 지울 수 없습니다.");
        }
        return ResponseEntity.noContent().build();
    }
}
