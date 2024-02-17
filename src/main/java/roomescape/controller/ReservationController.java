package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class ReservationController {
    private List<Reservation> reservations = List.of(
            new Reservation(1L, "허준기", LocalDate.now(), LocalTime.now()),
            new Reservation(2L, "허준보", LocalDate.now(), LocalTime.now()),
            new Reservation(3L, "허준", LocalDate.now(), LocalTime.now())
            );

    @GetMapping("/reservation")
    public String getReservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> reservations() {
        return reservations;
    }
}
