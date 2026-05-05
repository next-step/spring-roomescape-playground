package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.common.ViewNames;

@Controller
public class ReservationPageController {
    @GetMapping("/reservation")
    public String reservationPage() {
        return ViewNames.RESERVATION.getViewName();
    }
}
