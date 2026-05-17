package roomescape.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {
    @GetMapping("/")
    public String getHome() {
        return "home";
    }

    @GetMapping("/reservation")
    public String getReservation() {
        return "reservation";
    }
}
