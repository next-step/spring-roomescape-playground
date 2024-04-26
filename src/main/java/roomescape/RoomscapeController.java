package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RoomscapeController {

    @GetMapping
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String admin() {
        return "reservation";
    }
}
