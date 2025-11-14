package roomescape;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


@RestController
public class ReservationAPIController {
    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return Arrays.asList(
            new Reservation(1L,"브라운", "2023-10-15","10:00"),
            new Reservation(2L,"브라운","2025-11-01","19:00")
        );
    }
}
