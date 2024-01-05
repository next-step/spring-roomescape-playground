package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomescapeController {

    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        for (int i = 0; i < 3; ++i) {
            Reservation reservation = new Reservation(
                1L,
                "test" + i,
                LocalDate.now(),
                LocalTime.now()
            );
            reservations.add(reservation);
        }

        return ResponseEntity.ok().body(reservations);
    }
}
