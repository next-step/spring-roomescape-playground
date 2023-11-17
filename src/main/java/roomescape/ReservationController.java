package roomescape;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("reservation")
    public String reservation(){
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read(){
        return ResponseEntity.ok().body(reservations);
    }
}
