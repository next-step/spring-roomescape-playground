package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoomescapeController {
    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String showReservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> showReservations() {
        showReservationPage();
        List<Reservation> reservationList = insertReservation();
        return reservationList;
    }

    private List<Reservation> insertReservation() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(1L, "a", LocalDate.now(), LocalTime.now()));
        reservations.add(new Reservation(2L, "b", LocalDate.now(), LocalTime.now()));
        reservations.add(new Reservation(3L, "c", LocalDate.now(), LocalTime.now()));
        return reservations;
    }
}
