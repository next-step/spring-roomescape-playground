package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ReservationController {
    private static final String VIEW_RESERVATION = "reservation";

    @GetMapping("/reservation")
    public String reservation() {
        return VIEW_RESERVATION;
    }
}
