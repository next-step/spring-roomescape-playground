package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>(List.of(
        new Reservation(1L, "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0)),
        new Reservation(2L, "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 0)),
        new Reservation(3L, "브라운", LocalDate.of(2023, 1, 3), LocalTime.of(12, 0))
    ));

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservationPage(Model model) {
        model.addAttribute("reservations", reservations);
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponse> getReservations() {
        return reservations.stream()
            .map(ReservationResponse::from)
            .collect(Collectors.toList());
    }
}
