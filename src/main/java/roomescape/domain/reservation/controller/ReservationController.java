package roomescape.domain.reservation.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import roomescape.domain.reservation.domain.Reservation;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>(Arrays.asList(
        // new Reservation(1L, "이현수", LocalDate.now(), LocalTime.now()),
        // new Reservation(2L, "이현수", LocalDate.now(), LocalTime.now()),
        // new Reservation(3L, "이현수", LocalDate.now(), LocalTime.now())
    ));
    private AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
            .filter(it -> it.getId().equals(id))
            .findFirst()
            .orElseThrow(RuntimeException::new);

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> reservations(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
            .filter(it -> it.getId().equals(id))
            .findFirst()
            .orElseThrow(RuntimeException::new);

        return ResponseEntity.ok().body(reservation);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok().body(reservations);
    }
}
