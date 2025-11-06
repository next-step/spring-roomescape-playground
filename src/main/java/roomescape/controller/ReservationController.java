package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    public ReservationController() {
        reservations.add(new Reservation(index.getAndIncrement(), "브라운", LocalDate.parse("2023-01-01"), LocalTime.parse("10:00")));
        reservations.add(new Reservation(index.getAndIncrement(), "브라운", LocalDate.parse("2023-01-02"), LocalTime.parse("11:00")));
        reservations.add(new Reservation(index.getAndIncrement(), "브라운", LocalDate.parse("2023-01-03"), LocalTime.parse("12:00")));
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok().body(reservations);
    }
}
