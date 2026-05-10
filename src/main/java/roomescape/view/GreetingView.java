package roomescape.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetingView {

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
