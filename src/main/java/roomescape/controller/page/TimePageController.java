package roomescape.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/time")
public class TimePageController {

    @GetMapping
    public String time() {
        return "time";
    }
}
