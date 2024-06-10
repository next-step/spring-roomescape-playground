package roomescape.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String getReservation() {
        return "new-reservation";
    }

    @GetMapping("/time")
    public String getReservationTime() {
        return "time";
    }
}
