package roomescape;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class RoomscapeController {

    private List<Reservation> reservations = List.of(
            new Reservation(1L, "브라운", "2023-01-01", "10:00"),
            new Reservation(2L, "브라운", "2023-01-02", "11:00")
    );

    @GetMapping
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String admin() {
        return "reservation";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<Reservation> readAllReservations() {
        return reservations;
    }

}
