package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservations")
public class ViewController {
    @GetMapping("/reservationpage")
    public String reservationPage() {
        return "new-reservation";
    }
}
