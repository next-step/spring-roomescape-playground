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
import java.util.stream.Collectors;

@Controller
public class RoomescapeController {

    private final List<Reservation> reservations = new ArrayList<>();

    public RoomescapeController() {
        reservations.add(new Reservation(1L, "오찌", LocalDate.of(2025, 6, 2), LocalTime.of(17, 0)));
        reservations.add(new Reservation(2L, "장순", LocalDate.of(2025, 6, 2), LocalTime.of(17, 0)));
        reservations.add(new Reservation(3L, "희정", LocalDate.of(2025, 6, 2), LocalTime.of(17, 0)));
        reservations.add(new Reservation(4L, "예진", LocalDate.of(2025, 6, 2), LocalTime.of(17, 0)));
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponse> reservations() {
        return reservations.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }
}
