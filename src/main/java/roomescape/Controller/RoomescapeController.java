package roomescape.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RoomescapeController {

    private List<Reservation> reservations = new ArrayList<>();

    public RoomescapeController() {
        reservations.add(new Reservation(1, "오찌", "2025-06-02", "14:00"));
        reservations.add(new Reservation(2, "희정", "2025-06-02", "16:00"));
        reservations.add(new Reservation(3, "장순", "2025-06-02", "18:00"));
        reservations.add(new Reservation(4, "예진", "2025-06-02", "20:00"));
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservationPage(Model model) {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> reservations() {
        return reservations;
    }
}
