package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.List;


@Controller
public class ReservationController {
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

}
