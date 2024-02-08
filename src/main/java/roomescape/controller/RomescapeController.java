package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RomescapeController {

    @GetMapping("/")
    public String admin() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }
}
