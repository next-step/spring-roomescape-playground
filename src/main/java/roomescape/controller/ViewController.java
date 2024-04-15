package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String mainPage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservationView() {
        return "new-reservation";
    }

    @GetMapping("/time")
    public String timeView() {
        return "time";
    }
}