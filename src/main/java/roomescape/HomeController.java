package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        reservations.add(new Reservation(1L, "브라운", "2023-01-01", "10:00"));
        reservations.add(new Reservation(2L, "하이", "2024-05-23", "10:00"));
        reservations.add(new Reservation(2L, "바이", "2026-08-12", "10:00"));

        return ResponseEntity.ok().body(reservations);
    }

}
