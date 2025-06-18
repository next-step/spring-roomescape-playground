package roomescape.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ReservationApiController {
    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        reservations.add(new Reservation(1L, "브라운", LocalDate.now(), LocalTime.now()));
        reservations.add(new Reservation(2L, "브라운",LocalDate.now(), LocalTime.now()));
        reservations.add(new Reservation(3L, "브라운",LocalDate.now(), LocalTime.now()));

        return ResponseEntity.ok().body(reservations);
    }
}
