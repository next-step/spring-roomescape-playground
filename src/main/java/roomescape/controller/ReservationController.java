package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationController {
    private static final String VIEW_RESERVATION = "new-reservation";

    @GetMapping("/reservation")
    public String reservation() {
        return VIEW_RESERVATION;
    }
}
