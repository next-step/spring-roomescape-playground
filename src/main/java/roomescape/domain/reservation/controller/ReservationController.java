package roomescape.domain.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.domain.reservation.entity.Reservation;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> readReservations() {
        return ResponseEntity.ok().body(reservations);
    }
}