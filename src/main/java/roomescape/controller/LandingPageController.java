package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.common.ViewNames;

@Controller
public class LandingPageController {
    @GetMapping("/")
    public String landingPage() {
        return ViewNames.HOME.getViewName();
    }
}
