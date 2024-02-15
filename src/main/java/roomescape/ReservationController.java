package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    @GetMapping("/")
    public ResponseEntity<List<Reservation>> reservation(){
        return ResponseEntity.ok(reservations);
    }

}
