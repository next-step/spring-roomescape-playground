package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    // to test
    {
        Reservation test1 = new Reservation(index.getAndIncrement(), "name1", "2023-08-07", "11:00");
        Reservation test2 = new Reservation(index.getAndIncrement(), "name2", "2023-08-08", "12:00");
        Reservation test3 = new Reservation(index.getAndIncrement(), "name3", "2023-08-09", "13:00");

        reservations.add(test1);
        reservations.add(test2);
        reservations.add(test3);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations);
    }
}
