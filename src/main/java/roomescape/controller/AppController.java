package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/")
    public String viewHome() {

        return "home";
    }

    @GetMapping("/reservation")
    public String viewReservation(){

        return "new-reservation";
    }
}
