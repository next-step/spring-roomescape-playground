package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody // @Controller + @ResponseBody = @RestController
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>(List.of(
            new Reservation(1L, "브라운", LocalDate.parse("2023-01-01"), LocalTime.parse("10:00")),
            new Reservation(2L, "브라운", LocalDate.parse("2023-01-02"), LocalTime.parse("11:00"))
    ));

    @GetMapping("/reservations")
    public List<ReservationResponse> getReservations() {
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }
}
