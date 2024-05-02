package roomescape;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    @GetMapping("/reservation")
    public void reservation () {
    }

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
//        reservations.add(new Reservation(1,"브라운", "2023-01-01", "10:00"));
//        reservations.add(new Reservation(2,"브라운", "2023-01-02", "11:00"));

        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create (@RequestBody ReservationRequestDto request) {
        long id = index.incrementAndGet();

        Reservation newReservation = new Reservation(id, request.getName(), request.getDate(), request.getTime());
        reservations.add(newReservation);

        URI location = URI.create("/reservations/" + id);

        return ResponseEntity.created(location).body(newReservation);
    }
}
