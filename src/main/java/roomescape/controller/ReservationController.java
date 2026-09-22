package roomescape.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {
    private List<Reservation> reservations =  new ArrayList<>(List.of(
        new Reservation(1L, "브라운", "2026-09-22", "10:11"),
        new Reservation(2L, "브라운", "2026-09-23", "10:12"),
        new Reservation(3L, "브라운", "2026-09-24", "10:13")
    ));

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        return reservations;
    }
}
