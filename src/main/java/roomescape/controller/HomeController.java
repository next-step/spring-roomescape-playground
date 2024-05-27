package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String home() {
        return "home";
    }

    @GetMapping(value = "/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping(value =  "/time")
    public String time() {
        return "time";
    }
}
