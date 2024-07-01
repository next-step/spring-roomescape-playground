package roomescape.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>(Arrays.asList(
        new Reservation(
            1L,
            "정해성",
            LocalDate.of(2024, 6, 28),
            LocalTime.of(11, 0)
        ),
        new Reservation(
            2L,
            "김민재",
            LocalDate.of(2024, 6, 29),
            LocalTime.of(12, 0)
        ),
        new Reservation(
            3L,
            "김혜준",
            LocalDate.of(2024, 6, 30),
            LocalTime.of(12, 30)
        )
    ));

    @GetMapping("/reservation")
    public String reservation() { return "reservation"; }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok().body(reservations);
    }
}
