package roomescape;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>(
        List.of(
            new Reservation(1, "브라운", "2023-01-01", "10:00"),
            new Reservation(2, "브리", "2023-01-02", "11:00"),
            new Reservation(3, "포비", "2023-01-03", "12:00")
        )
    );

    @GetMapping("/reservation")
    public String adminPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        return ResponseEntity.ok(reservations);
    }
}
