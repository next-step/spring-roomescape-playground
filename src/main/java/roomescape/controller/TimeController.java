package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.model.Time;
import roomescape.repository.TimeRepository;

import java.util.ArrayList;
import java.util.List;


@Controller
public class TimeController {
    private final TimeRepository timeRepository;

    public TimeController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @GetMapping("/time")
    public String time() {
        return "time";
    }

}
