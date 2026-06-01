package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomescapeController {

    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String showReservationPage() {
        return "new-reservation";
    }

    @GetMapping("/time")
    public String showTimePage() {
        return "time";
    }
}
