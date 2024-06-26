package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.model.Reservation;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> reservations() {
        reservations.add(new Reservation(1L, "홍길동", "2024-07-01", "12:00"));
        reservations.add(new Reservation(2L, "신짱구", "2024-07-03", "14:00"));
        reservations.add(new Reservation(3L, "신짱아", "2024-07-04", "17:00"));
        return reservations;
    }
}
