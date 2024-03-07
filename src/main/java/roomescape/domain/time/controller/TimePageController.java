package roomescape.domain.time.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/time")
public class TimePageController {

    @GetMapping
    public String timePage() {
        return "time";
    }
}
