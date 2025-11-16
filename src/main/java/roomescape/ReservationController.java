package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();

    public ReservationController() {
        reservations.add(new Reservation(1L, "브라운", "2023-01-01", "10:00"));
        reservations.add(new Reservation(2L, "브라운", "2023-01-02", "11:00"));
        reservations.add(new Reservation(3L, "브라운", "2023-01-03", "13:00"));
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        ;
        return ResponseEntity
                .ok()
                .body(reservations);
    }
}
