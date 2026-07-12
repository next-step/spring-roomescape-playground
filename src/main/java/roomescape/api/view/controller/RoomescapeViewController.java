package roomescape.api.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomescapeViewController {

    @GetMapping("/")
    public String renderingHomePage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String renderingReservationPage() {
        return "new-reservation";
    }

    @GetMapping("/time")
    public String renderingTimePage() {
        return "time";
    }

}
