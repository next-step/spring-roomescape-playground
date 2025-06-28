package roomescape.time.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TimeController {
    @RequestMapping("/time")
    public String reservationPage() {
        return "time";
    }
}
