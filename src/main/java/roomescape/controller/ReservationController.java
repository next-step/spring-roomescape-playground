package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping(value = "/reservation")
    public String reservation() {
        return "reservation";
    }

    @ResponseBody
    @GetMapping(value = "/reservations")
    public List<Reservation> reservations() {
        reservations.add(createReservation(1L, "브라운", LocalDate.of(2024, 2, 1), LocalTime.of(10, 0, 0)));
        reservations.add(createReservation(2L, "브라운", LocalDate.of(2024, 2, 2), LocalTime.of(11, 0, 0)));
        reservations.add(createReservation(3L, "브라운", LocalDate.of(2024, 2, 3), LocalTime.of(12, 0, 0)));

        return reservations;
    }


    private Reservation createReservation(Long id, String name, LocalDate date, LocalTime time) {
        return new Reservation(id, name, date, time);
    }
}
