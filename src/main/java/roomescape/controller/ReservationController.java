package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.entity.Reservation;
import roomescape.service.ReservationService;

import java.util.List;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(value = "/reservation")
    public String getReservationPage() {
        return "reservation";
    }

    @ResponseBody
    @GetMapping(value = "/reservations")
    public List<Reservation> getReservations() {
        return reservationService.getReservations();
    }
}
