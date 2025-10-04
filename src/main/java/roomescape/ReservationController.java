package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {

    // Temporary data
    private final List<Reservation> reservations = new ArrayList<>(
            List.of(
                    new Reservation(1L, "브라운", "2023-01-01", "10:00"),
                    new Reservation(2L, "브라운", "2023-01-02", "11:00"),
                    new Reservation(3L, "브라운", "2023-01-03", "12:00")
            )
    );

    // render reservation.html
    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    // JSON list
    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> list() {
        return reservations;
    }
}
