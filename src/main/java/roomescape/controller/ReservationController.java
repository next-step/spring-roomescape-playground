package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import roomescape.domain.Reservation;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservation")
    public String reservation(){
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> Reservations(){

        reservations.add(new Reservation(1L,"브라운","2021-01-01","10:00"));
        reservations.add(new Reservation(2L,"브라운","2021-01-02","11:00"));
        reservations.add(new Reservation(3L,"브라운","2021-01-03","12:00"));

        return reservations;
    }
}
