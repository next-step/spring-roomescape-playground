package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    String home() {
        return "home.html";
    }

    @GetMapping("/reservation")
    String reservation() {
        return "new-reservation.html";
    }

    @GetMapping("/time")
    String time() {
        return "time.html";
    }
}
