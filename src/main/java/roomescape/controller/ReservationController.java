package roomescape.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.model.Reservation;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = List.of(
            new Reservation(1, "브라운", "2025-01-01", "10:00"),
            new Reservation(2, "브라운", "2025-01-02", "11:00"),
            new Reservation(3, "브라운", "2025-01-03", "12:00")
    );

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<Reservation> reservations() {
        return reservations;
    }
}
