package roomescape.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @RequestMapping("/")
    public String homePage() {
        return "home";
    }

    @RequestMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }
}
