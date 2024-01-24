package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomescapeController {

    @GetMapping("/")
    public String getHome() {
        return "home";
    }

    @GetMapping("/reservation")
    public String getReservationHtml() {
        return "new-reservation";
    }

    @GetMapping("/time")
    public String getTimeHtml() {
        return "time";
    }

}
