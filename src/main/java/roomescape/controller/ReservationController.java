package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import roomescape.domain.Reservation;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();

    public ReservationController(){
        reservations.add(new Reservation(1L, "치이카와", LocalDate.now(), LocalTime.now()));
        reservations.add(new Reservation(2L, "하치와레", LocalDate.now(), LocalTime.now()));
        reservations.add(new Reservation(3L, "우사기", LocalDate.now(), LocalTime.now()));
    }

    @GetMapping("reservation")
    public String reservation(){
        return "reservation";
    }

    @GetMapping("reservations")
    @ResponseBody
    public List<Reservation> reservations(){
        return reservations;
    }
}
