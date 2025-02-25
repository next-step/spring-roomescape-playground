package roomescape.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class ReservationViewController {

    @GetMapping("/reservation")
    public String readReservationPage() {
        return "new-reservation";
    }
}
