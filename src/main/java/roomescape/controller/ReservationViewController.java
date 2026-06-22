package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.service.ReservationService;

@Controller
public class ReservationViewController {

    private final ReservationService reservationService;

    public ReservationViewController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "new-reservation";
    }
}
