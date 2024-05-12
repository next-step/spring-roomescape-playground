package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0); //예약을 위한 ID

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservationList = new ArrayList<>();
        return ResponseEntity.status(HttpStatus.OK).body(reservationList);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation request) {
        int id = (int) index.incrementAndGet();
        Reservation reservation = new Reservation(id, request.getName(), request.getDate(), request.getTime());
        reservations.add(reservation);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/reservations/" + id)
                .body(reservation);
    }


}
