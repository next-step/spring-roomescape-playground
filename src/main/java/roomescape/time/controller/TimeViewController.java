package roomescape.time.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TimeViewController {
    @GetMapping("/time")
    public String TimePage() {
        return "time";
    }
}
