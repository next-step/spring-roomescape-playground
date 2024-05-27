package roomescape.time.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TimeController {

    @GetMapping("/time")
    public String time(){
        return "time";
    }
}
