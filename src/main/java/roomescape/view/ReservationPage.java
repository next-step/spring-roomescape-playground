package roomescape.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationPage {
    @GetMapping("/reservation")
    public String reservationPage() {
        return ViewNames.RESERVATION.getViewName();
    }
}
