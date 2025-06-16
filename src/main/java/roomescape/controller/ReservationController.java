package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.model.Reservation;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservationCheck() {
        reservations.add(new Reservation(1, "지수","2025-06-16", "14:00"));
        reservations.add(new Reservation(2, "치수","2025-06-15", "15:00"));
        reservations.add(new Reservation(3, "찌수","2025-06-14", "16:00"));
        return ResponseEntity.ok().body(this.reservations);
    }
}
