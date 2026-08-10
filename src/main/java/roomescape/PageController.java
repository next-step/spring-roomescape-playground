package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String Reservation() {
        return "reservation";
    }

    @GetMapping("/new-reservation")
    public String newReservation() {
        return "new-reservation";
    }
}
