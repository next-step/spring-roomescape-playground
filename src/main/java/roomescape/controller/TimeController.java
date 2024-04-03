package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//시간 홈 화면 반환
@Controller
public class TimeController {

    @GetMapping("/time")
    public String getTime() {
        return "time";
    }
}