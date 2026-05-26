package roomescape.reservations.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationViewController {

    @GetMapping("/")
    public String showPage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String showReservationPage() {
        return "new-reservation";
    }
}
