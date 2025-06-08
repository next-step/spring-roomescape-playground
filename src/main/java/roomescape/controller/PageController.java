package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String homePage() {
        return "home";  // templates/home.html 렌더링됨
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";  // templates/reservation.html
    }
}
