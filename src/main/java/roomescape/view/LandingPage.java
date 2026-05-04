package roomescape.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingPage {
    @GetMapping("/")
    public String landingPage() {
        return ViewNames.HOME.getViewName();
    }
}
