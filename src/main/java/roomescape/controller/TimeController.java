package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TimeController {
    private static final String VIEW_TIME = "time";

    @GetMapping("/time")
    public String home() {
        return VIEW_TIME;
    }
}
