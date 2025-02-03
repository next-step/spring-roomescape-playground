package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = createReservations();

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservations() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        return reservations;
    }

    private List<Reservation> createReservations() {
        return List.of(new Reservation(1, "파도", LocalDate.of(2025, 2, 2), LocalTime.of(9, 10)),
                new Reservation(2, "달", LocalDate.of(2025, 1, 14), LocalTime.of(10, 10)),
                new Reservation(3, "별", LocalDate.of(2025, 2, 1), LocalTime.of(10, 10)));
    }
}
