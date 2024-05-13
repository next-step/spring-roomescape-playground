package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.model.Reservation;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private int nextId = 1;

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    // 조회
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
}