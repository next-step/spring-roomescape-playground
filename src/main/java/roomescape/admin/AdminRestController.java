package roomescape.admin;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.admin.model.Reservation;

@RestController
public class AdminRestController {

    private final List<Reservation> reservations = new ArrayList<>();

    public AdminRestController() {
        reservations.add(new Reservation(1L, "브라운", LocalDate.of(2025, 6, 1), LocalTime.of(10, 0)));
        reservations.add(new Reservation(2L, "브라운", LocalDate.of(2025, 6, 2), LocalTime.of(11, 0)));
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        // @ResponseBody가 자동으로 적용됨
        return reservations;
    }
}
