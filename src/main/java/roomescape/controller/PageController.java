package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String getReservationPage() {
        return "new-reservation";
    }


    @GetMapping("/time")
    public String getTimePage() {
        return "time";
    }
}
