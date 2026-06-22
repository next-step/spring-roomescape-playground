package roomescape.controller;

import org.springframework.web.bind.annotation.GetMapping;
import roomescape.service.TimeService;


public class TimeViewController {

    private final TimeService timeService;

    public TimeViewController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/time")
    public String timePage() {
        return "time";
    }
}
