package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RoomescapeController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);



    @GetMapping("/")
    public String getHome() {
        return "home";
    }

    @GetMapping("/reservation")
    public String getReservation() {
        return "reservation";
    }
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        if (reservations.size() == 0) {
            for (int i = 0; i < 3; ++i) {
                Reservation reservation = new Reservation(
                    1L,
                    "test" + i,
                    LocalDate.now(),
                    LocalTime.now()
                );
                reservations.add(reservation);
            }
        }

        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestParam Reservation newReservation) {
        Reservation reservation = Reservation.toEntity(newReservation, index.getAndIncrement());
        reservations.add(reservation);
        return ResponseEntity.ok().body(reservation);
    }

}
