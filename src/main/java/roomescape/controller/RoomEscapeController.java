package roomescape.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomEscapeController {

    @GetMapping("/")
    public String home(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        return "home";
    }
}
