package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.model.Reservation;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservation")
    public String reserve() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reserveList() {
        reservations.add(new Reservation(1L, "브라운", "2024-01-01", "10:00"));
        reservations.add(new Reservation(2L, "브라움", "2024-01-02", "11:00"));
        reservations.add(new Reservation(3L, "브라웅", "2024-01-03", "12:00"));

        return ResponseEntity.ok().body(reservations);
    }

}
