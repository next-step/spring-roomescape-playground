package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

@Controller
public class ReservationController {
    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

}
