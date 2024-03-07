package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageViewController {
    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String showReservationOPage() {
        return "new-reservation";
    }

    @GetMapping("/time")
    public String showTimePage() {
        return "time";
    }
}
