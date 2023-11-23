package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomescapeController {
    @GetMapping("/")
    public String loadAdminPage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String loadReservationPage() {
        return "reservation";
    }
}
