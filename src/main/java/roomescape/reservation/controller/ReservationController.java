package roomescape.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReservationController {

    @RequestMapping("/")
    public String homePage() {
        return "home";
    }

    @RequestMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }
}
