package roomescape.reservation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/reservation")
    public String world() {
        return "reservation";
    }

}
