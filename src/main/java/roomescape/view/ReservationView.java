package roomescape.view;

import org.springframework.web.bind.annotation.GetMapping;

public class ReservationView {

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

}
