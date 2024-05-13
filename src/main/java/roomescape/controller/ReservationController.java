package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.Reservation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

//    @GetMapping("/reservations")
//    public ResponseEntity<List<Reservation>> getReservations() {
//        reservations.add(Reservation.toEntity(index.getAndIncrement(), "브라운", "2023-01-01", "10:00"));
//        reservations.add(Reservation.toEntity(index.getAndIncrement(), "브라운", "2023-01-02", "11:00"));
//        reservations.add(Reservation.toEntity(index.getAndIncrement(), "브라운", "2023-01-03", "12:00"));
//
//        return ResponseEntity.ok().body(reservations);
//    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Reservation newReservation = Reservation.toEntity(index.getAndIncrement(), reservation);
        reservations.add(newReservation);
        
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }


}