package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();

    public ReservationController() {
        initializeReservations();
    }

    private void initializeReservations() {
        reservations.add(new Reservation(
                1,
                "예약자1",
                LocalDate.of(2026, 8, 4),
                LocalTime.of(10, 0)
        ));
        reservations.add(new Reservation(
                2,
                "예약자2",
                LocalDate.of(2026, 8, 5),
                LocalTime.of(10, 10)
        ));
        reservations.add(new Reservation(
                3,
                "예약자3",
                LocalDate.of(2026, 8, 6),
                LocalTime.of(10, 20)
        ));
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<Reservation> reservations() {
        return List.copyOf(reservations);
    }
}
