package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String showAdminPage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String showReservationManagementPage() {
        return "reservation";
    }

    @GetMapping("/time")
    public String showTimeManagementPage() {
        return "time";
    }
}
