package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ReservationController {

    @RequestMapping(value = "/reservation", method = RequestMethod.GET)
    public String reservation() {
        return "reservation";
    }
}
