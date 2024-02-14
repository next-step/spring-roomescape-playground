package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = List.of(
        new Reservation(1L, "브라운", LocalDate.now(), LocalTime.now()),
        new Reservation(2L, "브라운", LocalDate.now(), LocalTime.now()),
        new Reservation(3L, "브라운", LocalDate.now(), LocalTime.now())
    );

    @GetMapping("/reservation")
    public String getReservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponse> getReservations() {
        return reservations.stream()
            .map(ReservationResponse::from)
            .toList();
    }
}
