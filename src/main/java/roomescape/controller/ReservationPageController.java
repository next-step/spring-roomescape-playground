package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ReservationPageController {

    @GetMapping("/reservation")
    public String reservationPage() {
        return "new-reservation";
    }
}
