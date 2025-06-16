package roomescape.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReservationApiController {
    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        reservations.add(new Reservation(1, "브라운","2023-01-01","10:00"));
        reservations.add(new Reservation(2, "브라운","2023-01-02","11:00"));
        reservations.add(new Reservation(3, "브라운","2023-01-03","12:00"));

        return ResponseEntity.ok().body(reservations);
    }
}
