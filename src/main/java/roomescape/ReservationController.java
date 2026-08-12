package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> reservations() {

        List<Reservation> reservations = new ArrayList<>();

        reservations.add(
                new Reservation(1L, "브라운", "2026-08-12", "10:00")
        );

        reservations.add(
                new Reservation(2L, "브라운", "2026-08-13", "11:00")
        );

        reservations.add(
                new Reservation(3L, "브라운", "2026-08-14", "12:00")
        );

        return reservations;
    }
}
