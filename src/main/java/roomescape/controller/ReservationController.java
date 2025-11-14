package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.model.Reservation;

import java.util.ArrayList;
import java.util.List;


@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

}
