package roomescape.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "home.html";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "new-reservation.html";
    }

    @GetMapping("/time")
    public String time() {
        return "time.html";
    }
}
