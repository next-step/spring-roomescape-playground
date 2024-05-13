package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class StepController {

    @GetMapping("/")
    public String viewHome() {

        return "home";
    }

    @GetMapping("/reservation")
    public String viewReservation(){

        return "reservation";
    }
}
