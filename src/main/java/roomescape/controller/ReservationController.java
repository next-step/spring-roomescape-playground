package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.Reservation;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>(
            List.of(new Reservation(1L, "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 00)),
                    new Reservation(2L, "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 00)),
                    new Reservation(3L, "브라운", LocalDate.of(2023, 1, 3), LocalTime.of(12, 00))));

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> reservations() {
        return reservations;
    }

}
