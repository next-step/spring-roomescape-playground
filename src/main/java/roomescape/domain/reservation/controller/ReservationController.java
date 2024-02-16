package roomescape.domain.reservation.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import roomescape.domain.reservation.domain.Reservation;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>(Arrays.asList(
        new Reservation(1L, "이현수", LocalDate.now(), LocalTime.now()),
        new Reservation(2L, "이현수", LocalDate.now(), LocalTime.now()),
        new Reservation(3L, "이현수", LocalDate.now(), LocalTime.now())
    ));
    private AtomicLong index = new AtomicLong(4);

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok().body(reservations);
    }
}
