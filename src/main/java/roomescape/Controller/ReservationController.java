package roomescape.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.Domain.Reservation;

@RestController
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();

    public ReservationController() {
        reservations.add(new Reservation(1, "브라운", LocalDate.parse("2023-01-01"), LocalTime.parse("10:00")));
        reservations.add(new Reservation(2, "브라운", LocalDate.parse("2023-01-02"), LocalTime.parse("10:00")));
        reservations.add(new Reservation(3, "브라운", LocalDate.parse("2023-01-03"), LocalTime.parse("10:00")));
    }

    @GetMapping("/reservations")
    public List<Reservation> findAll() {
        return reservations;
    }
}
