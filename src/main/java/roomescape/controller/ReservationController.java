package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.model.Reservation;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>(
            List.of(new Reservation(1L, "브라운", "2023-01-01", "10:00"),
                    new Reservation(2L, "브라운", "2023-01-02", "10:00"),
                    new Reservation(3L,"브라운","2023-01-03","10:00")));

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> reservations() {
        return reservations;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }
}
