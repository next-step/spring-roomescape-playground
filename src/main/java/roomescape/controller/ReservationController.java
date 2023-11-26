package roomescape.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import roomescape.dto.Reservation;
import roomescape.service.ReservationService;

@Controller
public class ReservationController {
    private final ReservationService reservationService = new ReservationService();

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<Reservation> reservations() {
        return reservationService.getAllReservations();
    }
}
