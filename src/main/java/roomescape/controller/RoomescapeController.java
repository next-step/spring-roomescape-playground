package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomescapeController {
    @GetMapping("/")
    public String loadHome() {
        return "home";
    }
}
