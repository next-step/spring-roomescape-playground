package roomescape.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.common.PagePath;

@Controller
public class LandingPage {
    @GetMapping(PagePath.HOME_PAGE_PATH)
    public String landingPage() {
        return ViewNames.HOME.getViewName();
    }
}
