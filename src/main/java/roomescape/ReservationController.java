package roomescape;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();

    public ReservationController() {
        reservations.add(new Reservation(1, "브라운", "2023-01-01", "10:00"));
        reservations.add(new Reservation(2, "브라운", "2023-01-02", "11:00"));
    }
    @GetMapping("/reservation")
    public String reservations() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> Reservations() {
        return reservations;
    }
}
