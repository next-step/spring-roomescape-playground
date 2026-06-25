package roomescape.common.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String showHome() {
        return "home";
    }

    @GetMapping("/reservation")
    public String showReservation() {
        return "new-reservation";
    }
}