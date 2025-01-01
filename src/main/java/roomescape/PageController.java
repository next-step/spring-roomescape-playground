package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/reservation")
    public String readReservationPage(Model model) {
        return "new-reservation";
    }

    @GetMapping("/time")
    public String readTimePage(Model model) {
        return "time";
    }
}
