package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservationController() {
        return "new-reservation";
    }

    @GetMapping("time")
    public String timeController() {
        return "time";
    }
}
