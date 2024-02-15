package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.Reservation;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> reservations(){
        return ResponseEntity.ok(reservationService.loadReservationList());
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservations(@RequestBody Reservation reservation){
        Reservation newReservation = reservationService.createReservation(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id){
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
